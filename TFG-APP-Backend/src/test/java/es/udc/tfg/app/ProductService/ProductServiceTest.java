package es.udc.tfg.app.ProductService;

import es.udc.tfg.app.model.Category.Category;
import es.udc.tfg.app.model.Product.Product;
import es.udc.tfg.app.model.ProductTaxes.ProductTaxes;
import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.service.categoryService.CategoryData;
import es.udc.tfg.app.service.categoryService.CategoryService;
import es.udc.tfg.app.service.productService.ProductData;
import es.udc.tfg.app.service.productService.ProductService;
import es.udc.tfg.app.service.productTaxesService.ProductTaxesData;
import es.udc.tfg.app.service.productTaxesService.ProductTaxesService;
import es.udc.tfg.app.service.userService.RegisterData;
import es.udc.tfg.app.service.userService.UserService;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import es.udc.tfg.app.util.exceptions.InvalidProductStockException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductTaxesService productTaxesService;

    private final String VALID_REFERENCE = "REF-001";
    private final String VALID_NAME = "Portátil";
    private final String VALID_DESCRIPTION = "Portátil de última xeración";
    private final Float VALID_PRICE = 999.99f;
    private final Integer VALID_DISCOUNT = 10;
    private final Integer VALID_STOCK = 5;

    private final String INVALID_NAME = "";
    private final Integer INVALID_STOCK = -1;

    private ProductData getValidProductData(Long categoryId, Long taxTypeId) {
        return new ProductData(
                VALID_REFERENCE,
                VALID_NAME,
                VALID_DESCRIPTION,
                null,
                null,
                VALID_PRICE,
                VALID_DISCOUNT,
                VALID_STOCK,
                taxTypeId,
                categoryId
        );
    }

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

    private Category getCategory(Long creatorId) throws Exception {
        CategoryData categoryData = new CategoryData("Electrónica","Categoría de produtos electrónicos");
       return categoryService.createCategory(categoryData, creatorId);
    }

    private ProductTaxes getTaxType() throws Exception {
        ProductTaxesData productTaxesData = new ProductTaxesData("IVA 21%","Imposto xeral",21f);
        return productTaxesService.createProductTaxes(productTaxesData);
    }

    @Test
    public void testCreateAndFindProductById() throws Exception {
        User creator = getCreatorUser();
        Category category = getCategory(creator.getId());
        ProductTaxes taxType = getTaxType();

        Product product = productService.createProduct(getValidProductData(category.getId(), taxType.getId()), creator.getId());

        Product found = productService.findProductById(product.getId());

        assertEquals(VALID_REFERENCE, found.getReference());
        assertEquals(VALID_NAME, found.getName());
        assertEquals(VALID_DESCRIPTION, found.getDescription());
        assertEquals(VALID_PRICE, found.getPrice());
        assertEquals(VALID_DISCOUNT, found.getDiscount());
        assertEquals(VALID_STOCK, found.getStock());
        assertEquals(category.getId(), found.getCategory().getId());
        assertEquals(taxType.getId(), found.getTaxType().getId());
        assertEquals(creator.getId(), found.getCreator().getId());
        assertNotNull(found.getCreateDate());
    }

    @Test(expected = InputValidationException.class)
    public void testCreateProductInvalidName() throws Exception {
        User creator = getCreatorUser();
        Category category = getCategory(creator.getId());
        ProductTaxes taxType = getTaxType();

        ProductData invalidData = new ProductData(
                VALID_REFERENCE,
                INVALID_NAME,
                VALID_DESCRIPTION,
                null,
                null,
                VALID_PRICE,
                VALID_DISCOUNT,
                VALID_STOCK,
                taxType.getId(),
                category.getId()
        );
        productService.createProduct(invalidData, creator.getId());
    }

    @Test(expected = InvalidProductStockException.class)
    public void testCreateProductInvalidStock() throws Exception {
        User creator = getCreatorUser();
        Category category = getCategory(creator.getId());
        ProductTaxes taxType = getTaxType();

        ProductData invalidData = new ProductData(
                VALID_REFERENCE,
                VALID_NAME,
                VALID_DESCRIPTION,
                null,
                null,
                VALID_PRICE,
                VALID_DISCOUNT,
                INVALID_STOCK,
                taxType.getId(),
                category.getId()
                );
        productService.createProduct(invalidData, creator.getId());
    }

    @Test
    public void testUpdateProduct() throws Exception {
        User creator = getCreatorUser();
        Category category = getCategory(creator.getId());
        ProductTaxes taxType = getTaxType();

        Product product = productService.createProduct(getValidProductData(category.getId(), taxType.getId()), creator.getId());

        ProductData updatedData = new ProductData(
                "REF-002",
                "Tablet",
                "Tablet de última xeración",
                null,
                null,
                499.99f,
                5,
                20,
                taxType.getId(),
                category.getId()
                );

        productService.updateProduct(product.getId(), updatedData);

        assertEquals("REF-002", product.getReference());
        assertEquals("Tablet", product.getName());
        assertEquals("Tablet de última xeración", product.getDescription());
        assertEquals(499.99f, product.getPrice());
        assertEquals(5, product.getDiscount());
        assertEquals(20, product.getStock());
    }

    @Test(expected = InstanceNotFoundException.class)
    public void testUpdateProductNotFound() throws Exception {
        productService.updateProduct(999L, null);
    }

    @Test
    public void testModifyProductStock() throws Exception {
        User creator = getCreatorUser();
        Category category = getCategory(creator.getId());
        ProductTaxes taxType = getTaxType();

        Product product = productService.createProduct(getValidProductData(category.getId(), taxType.getId()), creator.getId());

        productService.modifyProductStock(product.getId(), 50);

        assertEquals(50, product.getStock());
    }

    @Test(expected = InvalidProductStockException.class)
    public void testModifyProductStockInvalid() throws Exception {
        User creator = getCreatorUser();
        Category category = getCategory(creator.getId());
        ProductTaxes taxType = getTaxType();

        Product product = productService.createProduct(getValidProductData(category.getId(), taxType.getId()), creator.getId());

        productService.modifyProductStock(product.getId(), INVALID_STOCK);
    }

    @Test
    public void testFindProductByKeyword() throws Exception {
        User creator = getCreatorUser();
        Category category = getCategory(creator.getId());
        ProductTaxes taxType = getTaxType();

        productService.createProduct(getValidProductData(category.getId(), taxType.getId()), creator.getId());

        ProductData secondProductData = new ProductData(
                "REF-003",
                "Smartphone",
                "Teléfono intelixente",
                null,
                null,
                799.99f,
                15,
                30,
                taxType.getId(),
                category.getId()
        );
        productService.createProduct(secondProductData, creator.getId());

        Block<Product> block = productService.findProductByKeyword("Smartphone", category.getId(), 0, 10);

        assertEquals(1, block.getItems().size());
        assertEquals("Smartphone", block.getItems().get(0).getName());
    }
}
