package es.udc.tfg.app.service.noteService;

import es.udc.tfg.app.model.Client.Client;
import es.udc.tfg.app.model.Client.ClientDao;
import es.udc.tfg.app.model.Note.Note;
import es.udc.tfg.app.model.Noteline.Noteline;
import es.udc.tfg.app.model.Note.NoteDao;
import es.udc.tfg.app.model.Noteline.NotelineDao;
import es.udc.tfg.app.model.Product.Product;
import es.udc.tfg.app.model.Product.ProductDao;
import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.model.user.UserDao;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import es.udc.tfg.app.util.exceptions.InvalidProductStockException;
import es.udc.tfg.app.util.exceptions.InvoiceAttachedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Objects;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteDao noteDao;

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private NotelineDao notelineDao;

    @Override
    public Note createNote(NoteData noteData, Long creatorId) throws InstanceNotFoundException {
        Client client = clientDao.find(noteData.getClientId());
        User creator = userDao.find(creatorId);

        Note note = new Note( noteData.getComment(), client, creator);
        noteDao.save(note);

        return note;
    }

    @Override
    public void modifyNote(Long noteId, NoteData noteData) throws InstanceNotFoundException, InvoiceAttachedException {
        Note note = noteDao.find(noteId);

        if(note.getInvoice()!= null){
            throw new InvoiceAttachedException();
        }
        if (noteData.getClientId() != null){
            Long clientId = noteData.getClientId();
            if (!note.getClient().getId().equals(clientId) ) {
                Client client = clientDao.find(clientId);
                note.setClient(client);
            }
        }

        note.setComment(noteData.getComment());
    }

    @Override
    public void removeNote(Long noteId) throws InstanceNotFoundException, InvoiceAttachedException {
        Note note = noteDao.find(noteId);
        if (note.getInvoice() != null) {
            throw new InvoiceAttachedException();
        }
        noteDao.remove(note.getId());
    }

    @Override
    public Noteline addNoteline(NotelineData notelineData, Long noteId) throws InstanceNotFoundException, InvalidProductStockException, InvoiceAttachedException {
        Note note = noteDao.find(noteId);
        if(note.getInvoice()!= null){
            throw new InvoiceAttachedException();
        }
        Product product = productDao.find(notelineData.getProductId());
        if (product.getStock() < notelineData.getAmount()) {
            throw new InvalidProductStockException();
        }
        float productPrice = product.getFinalPrice();
        float notelineTotalPrice = product.getFinalPrice()*notelineData.getAmount()*(1-(notelineData.getDiscount()/100.00f));
        float notelineTaxValue = notelineTotalPrice*(product.getTaxType().getValue()/100.0f);

        Noteline noteline = new Noteline(
                note,
                product,
                productPrice,
                notelineData.getAmount(),
                notelineData.getDiscount(),
                notelineTaxValue,
                notelineData.getComment()
        );
        notelineDao.save(noteline);
        product.setStock(product.getStock() - notelineData.getAmount());
        note.getNotelines().add(noteline);
        note.setSubtotal(note.getSubtotal()+ notelineTotalPrice);
        note.setTaxes(note.getTaxes()+ notelineTaxValue);
        note.setTotal(note.getSubtotal() + note.getTaxes());

        return noteline;
    }

    @Override
    public void modifyNoteline(Long notelineId, NotelineData notelineData) throws InstanceNotFoundException, InvoiceAttachedException, InvalidProductStockException {
        Noteline noteline = notelineDao.find(notelineId);
        Note note = noteline.getNote();
        if (note.getInvoice() != null) {
            throw new InvoiceAttachedException();
        }
        float oldTotalPrice = noteline.getPrice() * noteline.getAmount()* (1 - noteline.getDiscount() / 100.0f);
        float oldTaxValue = noteline.getTaxes();
        Product oldProduct = noteline.getProduct();
        Long newProductId = notelineData.getProductId();
        float productPrice = oldProduct.getFinalPrice();

        if (!oldProduct.getId().equals(newProductId)) {
            oldProduct.setStock(oldProduct.getStock() + noteline.getAmount());
            Product newProduct = productDao.find(newProductId);
            productPrice = newProduct.getFinalPrice();

            if (newProduct.getStock() < notelineData.getAmount()) {
                throw new InvalidProductStockException();
            }
            newProduct.setStock(newProduct.getStock() - notelineData.getAmount());
            noteline.setProduct(newProduct);
        }

        noteline.setAmount(notelineData.getAmount());
        noteline.setDiscount(notelineData.getDiscount());
        noteline.setComment(notelineData.getComment());

        float notelineTotalPrice = productPrice * notelineData.getAmount()* (1 - notelineData.getDiscount() / 100.0f);
        float notelineTaxValue = notelineTotalPrice * (noteline.getProduct().getTaxType().getValue() / 100.0f);


        noteline.setPrice(productPrice);
        noteline.setTaxes(notelineTaxValue);

        note.setSubtotal(note.getSubtotal() - oldTotalPrice + notelineTotalPrice);
        note.setTaxes(note.getTaxes() - oldTaxValue + notelineTaxValue);
        note.setTotal(note.getSubtotal() + note.getTaxes());
    }

    @Override
    public void removeNoteline(Long notelineId) throws InstanceNotFoundException, InvoiceAttachedException {
        Noteline noteline = notelineDao.find(notelineId);
        Note note = noteline.getNote();
        if (note.getInvoice() != null) {
            throw new InvoiceAttachedException();
        }
        float totalPrice = noteline.getPrice() * noteline.getAmount();
        float taxValue = noteline.getTaxes();
        note.setSubtotal(note.getSubtotal() - totalPrice);
        note.setTaxes(note.getTaxes() - taxValue);
        note.setTotal(note.getSubtotal() + note.getTaxes());
        note.setTotal(note.getSubtotal() + note.getTaxes());

        notelineDao.remove(noteline.getId());
    }


    @Override
    public Note findNoteById(Long noteId) throws InstanceNotFoundException {
        return noteDao.find(noteId);
    }

    @Override
    public Noteline findNotelineById(Long notelineId) throws InstanceNotFoundException {
        return notelineDao.find(notelineId);
    }

    @Override
    public Block<Note> findNotes(Long clientId, Calendar startDate, Calendar endDate, int page, int size) {
        Slice<Note> notesSlice = noteDao.findByClientIdAndDateRange(clientId, startDate, endDate, page, size);
        return new Block<>(notesSlice.getContent(), notesSlice.hasNext());
    }
}

