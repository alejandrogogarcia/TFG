package es.udc.tfg.app.util.conversors;

import es.udc.tfg.app.model.ProductTaxes.ProductTaxes;
import es.udc.tfg.app.rest.dtos.ProductTaxesDto;
import java.util.List;
import java.util.stream.Collectors;


public class ProductTaxesConversor {

    public static ProductTaxesDto toProductTaxesDto(ProductTaxes productTaxes) {
        return new ProductTaxesDto(productTaxes.getId(), productTaxes.getType(), productTaxes.getDescription(), productTaxes.getValue());
    }

    public static List<ProductTaxesDto> toProductTaxesDto(List<ProductTaxes> productTaxes) {

        return productTaxes.stream().map(ProductTaxesConversor::toProductTaxesDto).collect(Collectors.toList());
    }
}
