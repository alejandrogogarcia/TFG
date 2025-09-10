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
import es.udc.tfg.app.util.conversors.DataConversor;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import es.udc.tfg.app.util.exceptions.InvalidProductStockException;
import es.udc.tfg.app.util.validator.ValidatorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import static es.udc.tfg.app.util.conversors.DataConversor.saveBase64ToFile;

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

    @Value("${app.product.path.final}")
    private String productMediaPath;

    @Value("${app.product.path.temp}")
    private String productMediaTempPath;


    @Override
    public Product createProduct(ProductData productData, Long creatorId) throws InstanceNotFoundException, InputValidationException, InvalidProductStockException, IOException {

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
        String unique = UUID.randomUUID().toString();

        String imageBase64 = productData.getImage();
        String imageTempPath = productMediaTempPath + unique + ".png";
        if (imageBase64 != null && !imageBase64.isBlank()) {
            saveBase64ToFile(imageBase64, imageTempPath);
        }

        String dataBase64 = productData.getData();
        String dataTempPath = productMediaTempPath + unique + ".pdf";
        if (dataBase64 != null && !dataBase64.isBlank()) {
            saveBase64ToFile(dataBase64, dataTempPath);
        }

        Product product = new Product(
                reference,
                name,
                description,
                null,
                null,
                productData.getPrice(),
                productData.getDiscount(),
                productData.getStock(),
                productTax,
                category,
                creator);

        productDao.save(product);

        Long productId = product.getId();

        if (Files.exists(Path.of(imageTempPath))) {
            String photoFileName = "product-" + productId + "-photo.png";
            Path finalImagePath = Path.of(productMediaPath, photoFileName);
            Files.move(Path.of(imageTempPath), finalImagePath, StandardCopyOption.REPLACE_EXISTING);
            product.setImage(photoFileName);
        }
        if (Files.exists(Path.of(dataTempPath))) {
            String dataFileName = "product-" + productId + "-data.pdf";
            Path finalDataPath = Path.of(productMediaPath, dataFileName);
            Files.move(Path.of(dataTempPath), finalDataPath, StandardCopyOption.REPLACE_EXISTING);
            product.setData(dataFileName);
        }

        return product;
    }

    @Override
    public void updateProduct(Long productId, ProductData productData) throws InstanceNotFoundException, InputValidationException, IOException {
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

        String unique = UUID.randomUUID().toString();

        String imageBase64 = productData.getImage();
        if (imageBase64 != null && !imageBase64.isBlank()) {
            String imageTempPath = productMediaTempPath + unique + ".png";
            saveBase64ToFile(imageBase64, imageTempPath);
            String photoFileName = "product-" + productId + "-photo.png";
            Path finalImagePath = Path.of(productMediaPath, photoFileName);
            Files.move(Path.of(imageTempPath), finalImagePath, StandardCopyOption.REPLACE_EXISTING);
            product.setImage(photoFileName);
        }else {
            product.setImage(null);
        }
        String dataBase64 = productData.getData();
        if (dataBase64 != null && !dataBase64.isBlank()) {
            String dataTempPath = productMediaTempPath + unique + ".pdf";
            saveBase64ToFile(dataBase64, dataTempPath);
            String dataFileName = "product-" + productId + "-data.pdf";
            Path finalDataPath = Path.of(productMediaPath, dataFileName);
            Files.move(Path.of(dataTempPath), finalDataPath, StandardCopyOption.REPLACE_EXISTING);
            product.setData(dataFileName);
        }else {
            product.setData(null);
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
