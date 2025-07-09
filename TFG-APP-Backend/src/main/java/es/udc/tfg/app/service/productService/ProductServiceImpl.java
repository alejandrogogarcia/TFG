package es.udc.tfg.app.service.productService;

import es.udc.tfg.app.model.Category.Category;
import es.udc.tfg.app.model.Category.CategoryDao;
import es.udc.tfg.app.model.Product.Product;
import es.udc.tfg.app.model.Product.ProductDao;
import es.udc.tfg.app.model.ProductTaxes.ProductTaxes;
import es.udc.tfg.app.model.ProductTaxes.ProductTaxesDao;
import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.model.user.UserDao;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import es.udc.tfg.app.util.exceptions.InvalidProductStockException;
import es.udc.tfg.app.util.validator.ValidatorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductTaxesDao productTaxesDao;


    @Override
    public Product createProduct(ProductData productData, Long creatorId) throws InstanceNotFoundException, InputValidationException, InvalidProductStockException {

        User creator = userDao.find(creatorId);
        Category category = categoryDao.find(productData.getCategoryId());
        ProductTaxes productTax = productTaxesDao.find(productData.getTaxTypeId());

        String reference = productData.getReference();
        ValidatorProperties.validateString(reference);
        String name = productData.getName();
        ValidatorProperties.validateString(name);
        String description = productData.getDescription();
        ValidatorProperties.validateString(description);
        int stock = productData.getStock();
        if (stock < 0){
            throw new InvalidProductStockException();
        }

        Product product = new Product(
                reference,
                name,
                description,
                productData.getImage(),
                productData.getData(),
                productData.getPrice(),
                productData.getDiscount(),
                productData.getStock(),
                productTax,
                category,
                creator);

        productDao.save(product);
        return product;
    }

    @Override
    public void updateProduct(Long productId, ProductData productData) throws InstanceNotFoundException, InputValidationException {
        Product product = productDao.find(productId);
        String newReference = productData.getReference();
        ValidatorProperties.validateString(newReference);
        String newName = productData.getName();
        ValidatorProperties.validateString(newName);
        String newDescription = productData.getDescription();
        ValidatorProperties.validateString(newDescription);

        Category category = categoryDao.find(productData.getCategoryId());
        ProductTaxes productTaxes = productTaxesDao.find(productData.getTaxTypeId());

        product.setReference(newReference);
        product.setName(newName);
        product.setDescription(newDescription);
        product.setPrice(productData.getPrice());
        product.setDiscount(productData.getDiscount());
        product.setStock(productData.getStock());
        product.setCategory(category);
        product.setTaxType(productTaxes);

        if (productData.getImage() != null) {
            product.setImage(productData.getImage());
        }

        if (productData.getData() != null) {
            product.setData(productData.getData());
        }
    }

    @Override
    public void modifyProductStock(Long productId, int stock) throws InstanceNotFoundException, InvalidProductStockException {

        Product product = productDao.find(productId);

        if (stock < 0) {
            throw new InvalidProductStockException();
        }
        product.setStock(stock);
    }

    @Override
    public Product findProductById(Long productId) throws InstanceNotFoundException {
        return productDao.find(productId);
    }

    @Override
    public Block<Product> findProductByKeyword(String keywords, Long categoryId, int page, int size) throws InstanceNotFoundException {
        Slice<Product> slice = productDao.findByKeywords(keywords, categoryId, page, size);
        return new Block<>(slice.getContent(), slice.hasNext());
    }
}
