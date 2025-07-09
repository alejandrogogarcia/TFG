package es.udc.tfg.app.service.productTaxesService;

import es.udc.tfg.app.model.ProductTaxes.ProductTaxes;
import es.udc.tfg.app.model.ProductTaxes.ProductTaxesDao;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import es.udc.tfg.app.util.validator.ValidatorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductTaxesServiceImpl implements ProductTaxesService{

    @Autowired
    private ProductTaxesDao productTaxesDao;

    @Override
    public ProductTaxes createProductTaxes(ProductTaxesData productTaxesData) throws InputValidationException {

        float value = productTaxesData.getValue();
        if (value < 0) {
            throw new InputValidationException("value","Should be greater than 0");
        }
        String taxType = productTaxesData.getType();
        String description = productTaxesData.getDescription();
        ValidatorProperties.validateString(description);
        ProductTaxes productTaxes = new ProductTaxes(taxType ,description, value);
        productTaxesDao.save(productTaxes);
        return productTaxes;
    }

    @Override
    public void updateProductTaxes(Long id ,ProductTaxesData productTaxesData) throws InputValidationException, InstanceNotFoundException {

        ProductTaxes productTax = productTaxesDao.find(id);
        float value = productTaxesData.getValue();
        if (value < 0) {
            throw new InputValidationException("value","Should be greater than 0");
        }
        String taxType = productTaxesData.getType();
        ValidatorProperties.validateString(taxType);
        String description = productTaxesData.getDescription();
        ValidatorProperties.validateString(description);
        productTax.setType(taxType);
        productTax.setDescription(description);
        productTax.setValue(value);
    }

    @Override
    public ProductTaxes findProductTaxesById(Long id) throws InstanceNotFoundException {
        return productTaxesDao.find(id);
    }

    @Override
    public List<ProductTaxes> findAllProductTaxes() {
        return productTaxesDao.getAllProductTaxes();
    }
}
