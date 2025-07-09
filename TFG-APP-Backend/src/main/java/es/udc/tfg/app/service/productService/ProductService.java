package es.udc.tfg.app.service.productService;

import es.udc.tfg.app.model.Product.Product;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import es.udc.tfg.app.util.exceptions.InvalidProductStockException;

public interface ProductService {

    public Product createProduct(ProductData productData, Long creatorId) throws InstanceNotFoundException, InputValidationException, InvalidProductStockException;

    public void updateProduct(Long productId, ProductData productData) throws InstanceNotFoundException, InputValidationException;

    public void modifyProductStock(Long productId, int stock) throws InstanceNotFoundException, InvalidProductStockException;

    public Product findProductById (Long productId) throws InstanceNotFoundException;

    public Block<Product> findProductByKeyword(String keyword, Long categoryId, int page, int size) throws InstanceNotFoundException;
}
