package es.udc.tfg.app.noteInvoiceService;

import es.udc.tfg.app.model.Category.Category;
import es.udc.tfg.app.model.Client.Client;
import es.udc.tfg.app.model.Invoice.Invoice;
import es.udc.tfg.app.model.Note.Note;
import es.udc.tfg.app.model.Noteline.Noteline;
import es.udc.tfg.app.model.Product.Product;
import es.udc.tfg.app.model.ProductTaxes.ProductTaxes;
import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.service.InvoiceService.InvoiceService;
import es.udc.tfg.app.service.categoryService.CategoryData;
import es.udc.tfg.app.service.categoryService.CategoryService;
import es.udc.tfg.app.service.clientService.ClientData;
import es.udc.tfg.app.service.clientService.ClientService;
import es.udc.tfg.app.service.noteService.NoteData;
import es.udc.tfg.app.service.noteService.NoteService;
import es.udc.tfg.app.service.noteService.NotelineData;
import es.udc.tfg.app.service.productService.ProductData;
import es.udc.tfg.app.service.productService.ProductService;
import es.udc.tfg.app.service.productTaxesService.ProductTaxesData;
import es.udc.tfg.app.service.productTaxesService.ProductTaxesService;
import es.udc.tfg.app.service.userService.RegisterData;
import es.udc.tfg.app.service.userService.UserService;
import es.udc.tfg.app.util.exceptions.CreateInvoiceException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import es.udc.tfg.app.util.exceptions.InvalidProductStockException;
import es.udc.tfg.app.util.exceptions.InvoiceAttachedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class NoteInvoiceServiceTest {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ProductTaxesService productTaxesService;

    private User getCreatorUser() throws Exception {
        RegisterData registerData = new RegisterData(
                "Pepe",
                "Pérez",
                "12345678A",
                "pepe@example.com",
                "01/01/1990",
                "ESP",
                "ADMIN",
                ""
        );
        User user = userService.registerUser(registerData);
        userService.setUserPassword(user.getDni(), user.getToken(), "passwordValido1!");
        return user;
    }

    private Client getClient(User creator) throws Exception {
        ClientData clientData = new ClientData("Cliente1","Apelido","11111111B","Rúa Principal 10","Santiago de Compostela",15701L,"cliente@example.com",600123456L);
        return clientService.createClient(creator.getId(), clientData);
    }

    private ProductTaxes getTaxType() throws Exception {
        ProductTaxesData productTaxesData = new ProductTaxesData("IVA 21%", "Imposto xeral", 21f);
        return productTaxesService.createProductTaxes(productTaxesData);
    }

    private static final String CATEGORY1_NAME        = "ELECTRÓNICA";
    private static final String CATEGORY1_DESCRIPTION = "Produtos electrónicos";
    private static final String CATEGORY2_NAME        = "HOGAR";
    private static final String CATEGORY2_DESCRIPTION = "Produtos para o fogar";

    private Category getCategory1(Long creatorId) throws Exception {
        CategoryData categoryData = new CategoryData(
                CATEGORY1_NAME,
                CATEGORY1_DESCRIPTION
        );
        return categoryService.createCategory(categoryData, creatorId);
    }

    private Category getCategory2(Long creatorId) throws Exception {
        CategoryData categoryData = new CategoryData(
                CATEGORY2_NAME,
                CATEGORY2_DESCRIPTION
        );
        return categoryService.createCategory(categoryData, creatorId);
    }

    private static final String PRODUCT1_REFERENCE   = "REF-001";
    private static final String PRODUCT1_NAME        = "PORTÁTIL";
    private static final String PRODUCT1_DESCRIPTION = "PORTÁTIL DE ÚLTIMA XERACIÓN";
    private static final Float  PRODUCT1_PRICE       = 1000f;
    private static final Integer PRODUCT1_DISCOUNT   = 0;
    private static final Integer PRODUCT1_STOCK      = 10;
    private static final String PRODUCT2_REFERENCE   = "REF-002";
    private static final String PRODUCT2_NAME        = "LAVADORA";
    private static final String PRODUCT2_DESCRIPTION = "LAVADORA DE 7KG";
    private static final Float  PRODUCT2_PRICE       = 500f;
    private static final Integer PRODUCT2_DISCOUNT   = 5;
    private static final Integer PRODUCT2_STOCK      = 20;

    private Product getProduct1(User creator, ProductTaxes taxType, Category category) throws Exception {
        ProductData productData = new ProductData(
                PRODUCT1_REFERENCE,
                PRODUCT1_NAME,
                PRODUCT1_DESCRIPTION,
                null,
                null,
                PRODUCT1_PRICE,
                PRODUCT1_DISCOUNT,
                PRODUCT1_STOCK,
                taxType.getId(),
                category.getId()
        );
        return productService.createProduct(productData, creator.getId());
    }

    private Product getProduct2(User creator, ProductTaxes taxType, Category category) throws Exception {
        ProductData productData = new ProductData(
                PRODUCT2_REFERENCE,
                PRODUCT2_NAME,
                PRODUCT2_DESCRIPTION,
                null,
                null,
                PRODUCT2_PRICE,
                PRODUCT2_DISCOUNT,
                PRODUCT2_STOCK,
                taxType.getId(),
                category.getId()
        );
        return productService.createProduct(productData, creator.getId());
    }

    @Test
    public void testCreateAndFindNote() throws Exception {
        User creator = getCreatorUser();
        Client client = getClient(creator);

        NoteData noteData = new NoteData("Primeira nota", client.getId());
        Note note = noteService.createNote(noteData, creator.getId());

        Note found = noteService.findNoteById(note.getId());

        assertEquals("Primeira nota", found.getComment());
        assertEquals(client.getId(), found.getClient().getId());
        assertEquals(creator.getId(), found.getCreator().getId());
    }

    @Test
    public void testModifyNote() throws Exception {
        User creator = getCreatorUser();
        Client client = getClient(creator);

        Note note = noteService.createNote(new NoteData("Nota inicial", client.getId()), creator.getId());
        noteService.modifyNote(note.getId(), new NoteData("Nota modificada", client.getId()));

        assertEquals("Nota modificada", note.getComment());
    }

    @Test(expected = InstanceNotFoundException.class)
    public void testModifyNoteNotFound() throws Exception {
        User creator = getCreatorUser();
        Client client = getClient(creator);
        noteService.modifyNote(999L, new NoteData("Nota inexistente", client.getId()));
    }

    @Test
    public void testAddNoteline() throws Exception {
        User creator = getCreatorUser();
        Client client = getClient(creator);
        ProductTaxes taxType = getTaxType();
        Category category = getCategory1(creator.getId());
        Product product = getProduct1(creator, taxType, category);

        Note note = noteService.createNote(new NoteData("Nota con liña", client.getId()), creator.getId());
        NotelineData notelineData = new NotelineData(product.getId(), null, 2, 0, null, "Liña de produto");
        Noteline noteline = noteService.addNoteline(notelineData, note.getId());

        assertEquals(product.getId(), noteline.getProduct().getId());
        assertEquals(2, noteline.getAmount());
        assertEquals("Liña de produto", noteline.getComment());
        assertTrue(note.getSubtotal() > 0);
        assertTrue(note.getTotal() > 0);
    }

    @Test(expected = InvalidProductStockException.class)
    public void testAddNotelineInvalidStock() throws Exception {
        User creator = getCreatorUser();
        Client client = getClient(creator);
        ProductTaxes taxType = getTaxType();
        Category category = getCategory1(creator.getId());
        Product product = getProduct1(creator, taxType, category);
        productService.modifyProductStock(product.getId(),0);
        Note note = noteService.createNote(new NoteData("Nota con stock inválido", client.getId()), creator.getId());
        NotelineData notelineData = new NotelineData(product.getId(), product.getPrice(), 999, 0, product.getTaxType().getValue(), "Liña de produto");
        noteService.addNoteline(notelineData, note.getId());
    }

    @Test
    public void testRemoveNoteline() throws Exception {
        User creator = getCreatorUser();
        Client client = getClient(creator);
        ProductTaxes taxType = getTaxType();
        Category category = getCategory1(creator.getId());
        Product product = getProduct1(creator, taxType, category);

        Note note = noteService.createNote(new NoteData("Nota con liña", client.getId()), creator.getId());
        Noteline noteline = noteService.addNoteline(new NotelineData(product.getId(), null, 1, 0, null, "Liña"), note.getId());

        noteService.removeNoteline(noteline.getId());
        assertEquals(0, note.getNotelines().size());
    }

    @Test
    public void testFindNotes() throws Exception {
        User creator = getCreatorUser();
        Client client = getClient(creator);

        noteService.createNote(new NoteData("Nota1", client.getId()), creator.getId());
        noteService.createNote(new NoteData("Nota2", client.getId()), creator.getId());

        Block<Note> block = noteService.findNotes(client.getId(), null, null, 0, 10);

        assertEquals(2, block.getItems().size());
    }

    @Test
    public void testCreateInvoiceFromNote() throws Exception {
        User creator = getCreatorUser();
        Client client = getClient(creator);
        ProductTaxes taxType = getTaxType();
        Category category = getCategory1(creator.getId());
        Product product = getProduct1(creator, taxType, category);

        Note note = noteService.createNote(new NoteData("Nota facturable", client.getId()), creator.getId());
        noteService.addNoteline(new NotelineData(product.getId(), null, 2, 0, null, "Liña"), note.getId());

        Invoice invoice = invoiceService.createInvoice(List.of(note.getId()), creator.getId());

        assertNotNull(invoice.getId());
        assertEquals(client.getId(), invoice.getClient().getId());
        assertEquals(note.getTotal(), invoice.getTotal());
        assertEquals(invoice, note.getInvoice());
    }

    @Test(expected = InvoiceAttachedException.class)
    public void testModifyNoteAfterInvoice() throws Exception {
        User creator = getCreatorUser();
        Client client = getClient(creator);
        ProductTaxes taxType = getTaxType();
        Category category = getCategory1(creator.getId());
        Product product = getProduct1(creator, taxType, category);

        Note note = noteService.createNote(new NoteData("Nota facturable", client.getId()), creator.getId());
        noteService.addNoteline(new NotelineData(product.getId(), null, 1, 0, null, "Liña"), note.getId());

        invoiceService.createInvoice(List.of(note.getId()), creator.getId());

        noteService.modifyNote(note.getId(), new NoteData("Cambio non permitido", client.getId()));
    }

    @Test(expected = CreateInvoiceException.class)
    public void testEmptyNotesList() throws Exception {
        User creator = getCreatorUser();
        invoiceService.createInvoice(Collections.emptyList(), creator.getId());
    }

    @Test(expected = CreateInvoiceException.class)
    public void testNoteAlreadyInvoiced() throws Exception {
        User creator = getCreatorUser();
        Client client = getClient(creator);
        ProductTaxes taxType = getTaxType();
        Category category = getCategory1(creator.getId());
        Product product = getProduct1(creator, taxType, category);

        Note note = noteService.createNote(new NoteData("Nota facturable", client.getId()), creator.getId());
        noteService.addNoteline(new NotelineData(product.getId(), null, 1, 0, null, "Liña"), note.getId());

        invoiceService.createInvoice(List.of(note.getId()), creator.getId());
        invoiceService.createInvoice(List.of(note.getId()), creator.getId());
    }

    @Test(expected = CreateInvoiceException.class)
    public void testNoteWithZeroTotal() throws Exception {
        User creator = getCreatorUser();
        Client client = getClient(creator);

        Note note = noteService.createNote(new NoteData("Nota baleira", client.getId()), creator.getId());

        invoiceService.createInvoice(List.of(note.getId()), creator.getId());
    }

    @Test(expected = CreateInvoiceException.class)
    public void testNotesWithDifferentClients() throws Exception {
        User creator = getCreatorUser();
        Client client1 = getClient(creator);
        Client client2 = clientService.createClient(creator.getId(),
                new ClientData("Cliente2","Apelido","22222222C","Outra rúa","A Coruña",15001L,"c2@example.com",600654321L));

        ProductTaxes taxType = getTaxType();
        Category category = getCategory1(creator.getId());
        Product product = getProduct1(creator, taxType, category);

        Note note1 = noteService.createNote(new NoteData("Nota cliente1", client1.getId()), creator.getId());
        noteService.addNoteline(new NotelineData(product.getId(), null, 1, 0, null, "Liña"), note1.getId());

        Note note2 = noteService.createNote(new NoteData("Nota cliente2", client2.getId()), creator.getId());
        noteService.addNoteline(new NotelineData(product.getId(), null, 1, 0, null, "Liña"), note2.getId());

        invoiceService.createInvoice(List.of(note1.getId(), note2.getId()), creator.getId());
    }
}
