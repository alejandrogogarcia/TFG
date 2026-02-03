package es.udc.tfg.app.productTaxesService;

import es.udc.tfg.app.model.ProductTaxes.ProductTaxes;
import es.udc.tfg.app.service.productTaxesService.ProductTaxesData;
import es.udc.tfg.app.service.productTaxesService.ProductTaxesService;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class productTaxesServiceTest {

    @Autowired
    private ProductTaxesService productTaxesService;

    private final String VALID_TYPE = "IVA 21%";
    private final String VALID_DESCRIPTION = "Imposto sobre o valor engadido";
    private final Float VALID_VALUE = 21.0f;

    private final String VALID_TYPE_2 = "IVA 10%";
    private final String VALID_DESCRIPTION_2 = "Imposto reducido";
    private final Float VALID_VALUE_2 = 10.0f;

    private final String INVALID_TYPE = "";
    private final String INVALID_DESCRIPTION = "";
    private final Float INVALID_VALUE = -5.0f;

    private ProductTaxesData getValidProductTaxesData1() {
        return new ProductTaxesData(VALID_TYPE, VALID_DESCRIPTION, VALID_VALUE);
    }

    private ProductTaxesData getValidProductTaxesData2() {
        return new ProductTaxesData(VALID_TYPE_2, VALID_DESCRIPTION_2, VALID_VALUE_2);
    }

    private ProductTaxes getValidProductTax() throws Exception {
        return productTaxesService.createProductTaxes(getValidProductTaxesData1());
    }

    @Test
    public void testCreateAndFindProductTaxesById() throws Exception {
        ProductTaxes tax = getValidProductTax();

        ProductTaxes found = productTaxesService.findProductTaxesById(tax.getId());

        assertEquals(VALID_TYPE, found.getType());
        assertEquals(VALID_DESCRIPTION, found.getDescription());
        assertEquals(VALID_VALUE, found.getValue());
        assertNotNull(found.getId());
    }

    @Test(expected = InputValidationException.class)
    public void testCreateProductTaxesInvalidValue() throws Exception {
        ProductTaxesData invalidData = new ProductTaxesData(VALID_TYPE, VALID_DESCRIPTION, INVALID_VALUE);
        productTaxesService.createProductTaxes(invalidData);
    }

    @Test(expected = InputValidationException.class)
    public void testCreateProductTaxesInvalidDescription() throws Exception {
        ProductTaxesData invalidData = new ProductTaxesData(VALID_TYPE, INVALID_DESCRIPTION, VALID_VALUE);
        productTaxesService.createProductTaxes(invalidData);
    }

    @Test
    public void testUpdateProductTaxes() throws Exception {
        ProductTaxes tax = getValidProductTax();

        productTaxesService.updateProductTaxes(tax.getId(), getValidProductTaxesData2());

        assertEquals(VALID_TYPE_2, tax.getType());
        assertEquals(VALID_DESCRIPTION_2, tax.getDescription());
        assertEquals(VALID_VALUE_2, tax.getValue());
    }

    @Test(expected = InstanceNotFoundException.class)
    public void testUpdateProductTaxesNotFound() throws Exception {
        productTaxesService.updateProductTaxes(999L, getValidProductTaxesData1());
    }

    @Test
    public void testFindAllProductTaxes() throws Exception {
        productTaxesService.createProductTaxes(getValidProductTaxesData1());
        productTaxesService.createProductTaxes(getValidProductTaxesData2());

        List<ProductTaxes> taxesList = productTaxesService.findAllProductTaxes();

        assertEquals(2, taxesList.size());
        assertTrue(taxesList.stream().anyMatch(t -> t.getType().equals(VALID_TYPE)));
        assertTrue(taxesList.stream().anyMatch(t -> t.getType().equals(VALID_TYPE_2)));
    }
}
