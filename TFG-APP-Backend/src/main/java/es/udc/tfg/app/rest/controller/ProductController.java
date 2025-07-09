package es.udc.tfg.app.rest.controller;

import es.udc.tfg.app.model.Product.Product;
import es.udc.tfg.app.rest.dtos.ProductDto;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.service.productService.ProductData;
import es.udc.tfg.app.service.productService.ProductService;
import es.udc.tfg.app.util.conversors.ProductConversor;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import es.udc.tfg.app.util.exceptions.InvalidProductStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductData productData, @RequestAttribute Long userId) throws InstanceNotFoundException, InputValidationException, InvalidProductStockException {

        Product product = productService.createProduct(productData, userId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").build().toUri();

        return ResponseEntity.created(location).body(ProductConversor.toProductDto(product));
    }

    @PutMapping("/{id}/update")
    public void updateProduct(@PathVariable Long id, @RequestBody ProductData productData) throws InstanceNotFoundException, InputValidationException {

        productService.updateProduct(id,productData);
    }

    @PutMapping("/{id}/stock")
    public void modifyProductStock( @PathVariable Long id, @RequestParam int stock) throws InstanceNotFoundException, InvalidProductStockException {
            productService.modifyProductStock(id, stock);
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id) throws InstanceNotFoundException {
        return ProductConversor.toProductDto(productService.findProductById(id));
    }

    @GetMapping("/find")
    public Block<ProductDto> findProducts(@RequestParam (required=false) String keywords,
                                          @RequestParam(required=false) Long categoryId,
                                          @RequestParam(defaultValue="0") int page) throws InstanceNotFoundException {
        Block<Product> productBlock = productService.findProductByKeyword(keywords != null ? keywords.trim() : null, categoryId, page, 5 );
        return new Block<>(ProductConversor.toProductDto(productBlock.getItems()),productBlock.getExistMoreItems());
    }

}
