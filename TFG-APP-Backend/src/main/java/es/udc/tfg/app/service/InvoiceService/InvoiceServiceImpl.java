package es.udc.tfg.app.service.InvoiceService;

import es.udc.tfg.app.model.Client.Client;
import es.udc.tfg.app.model.Invoice.Invoice;
import es.udc.tfg.app.model.Invoice.InvoiceDao;
import es.udc.tfg.app.model.Note.Note;
import es.udc.tfg.app.model.Note.NoteDao;
import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.model.user.UserDao;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.util.exceptions.CreateInvoiceException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Repository
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private NoteDao noteDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Invoice createInvoice(List<Long> notes, Long creatorId) throws InstanceNotFoundException, CreateInvoiceException {
        if (notes.isEmpty()) {
            throw new CreateInvoiceException("project.service.invoice.error.emptyNotes");
        }
        List<Note> noteList = new ArrayList<>();
        Client commonClient = null;
        float subtotal = 0, taxes = 0, total = 0;
        for (Long noteId : notes) {
            Note note = noteDao.find(noteId);
            if (note.getInvoice() != null) {
                throw new CreateInvoiceException("project.service.invoice.error.alreadyInvoiced");
            }
            if (note.getTotal() == 0.0f) {
                throw new CreateInvoiceException("project.service.invoice.error.cannotInvoiceZero");
            }
            if (commonClient == null) {
                commonClient = note.getClient();
            } else if (!commonClient.getId().equals(note.getClient().getId())) {
                throw new CreateInvoiceException("project.service.invoice.error.differentClients");
            }
            subtotal += note.getSubtotal();
            taxes += note.getTaxes();
            total += note.getTotal();
            noteList.add(note);
        }
        User creator = userDao.find(creatorId);
        Invoice invoice = new Invoice(subtotal, taxes, total, commonClient, creator);
        invoiceDao.save(invoice);
        for (Note note : noteList) {
            note.setInvoice(invoice);
        }
        return invoice;
    }

    @Override
    public Invoice findInvoiceById(Long invoiceId) throws InstanceNotFoundException {
        return invoiceDao.find(invoiceId);
    }

    @Override
    public Block<Invoice> findInvoices(Long clientId, Calendar startDate, Calendar endDate, int page, int size) {
        Slice<Invoice> invoicesSlice = invoiceDao.findByClientIdAndDateRange(clientId, startDate, endDate, page, size);
        return new Block<>(invoicesSlice.getContent(), invoicesSlice.hasNext());
    }
}