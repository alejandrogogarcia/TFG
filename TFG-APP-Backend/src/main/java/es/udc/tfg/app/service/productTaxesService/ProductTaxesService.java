package es.udc.tfg.app.service.productTaxesService;

import es.udc.tfg.app.model.ProductTaxes.ProductTaxes;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;

import java.util.List;

public interface ProductTaxesService {

    public ProductTaxes createProductTaxes(ProductTaxesData productTaxesData) throws InputValidationException;

    public void updateProductTaxes(Long id ,ProductTaxesData productTaxesData) throws InputValidationException, InstanceNotFoundException;

    public ProductTaxes findProductTaxesById(Long id) throws InstanceNotFoundException;

    public List<ProductTaxes> findAllProductTaxes();
}
