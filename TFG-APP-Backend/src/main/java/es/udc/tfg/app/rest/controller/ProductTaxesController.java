package es.udc.tfg.app.rest.controller;

import es.udc.tfg.app.model.ProductTaxes.ProductTaxes;
import es.udc.tfg.app.rest.dtos.ProductTaxesDto;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.service.productTaxesService.ProductTaxesData;
import es.udc.tfg.app.service.productTaxesService.ProductTaxesService;
import es.udc.tfg.app.util.conversors.ProductTaxesConversor;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/productTax")
public class ProductTaxesController {

    @Autowired
    private ProductTaxesService productTaxesService;

    @PostMapping("/create")
    public ResponseEntity<ProductTaxesDto> createProductTax(@RequestBody ProductTaxesData productTaxesData) throws InputValidationException {

        ProductTaxes productTaxes = productTaxesService.createProductTaxes(productTaxesData);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").build().toUri();

        return ResponseEntity.created(location).body(ProductTaxesConversor.toProductTaxesDto(productTaxes));
    }

    @PutMapping("/{id}/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProductTax(@PathVariable Long id, @RequestBody ProductTaxesData productTaxesData) throws InputValidationException, InstanceNotFoundException {
        productTaxesService.updateProductTaxes(id, productTaxesData);
    }

    @GetMapping("/{id}")
    public ProductTaxesDto findProductTax(@PathVariable Long id) throws InstanceNotFoundException {
        return ProductTaxesConversor.toProductTaxesDto(productTaxesService.findProductTaxesById(id));
    }

    @GetMapping("/all")
    public List<ProductTaxesDto> findProductTaxes(){
        return ProductTaxesConversor.toProductTaxesDto(productTaxesService.findAllProductTaxes());

    }
}
