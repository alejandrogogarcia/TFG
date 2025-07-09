package es.udc.tfg.app.util.conversors;

import es.udc.tfg.app.model.Product.Product;
import es.udc.tfg.app.rest.dtos.ProductDto;

import java.util.List;
import java.util.stream.Collectors;

public class ProductConversor {
    public static ProductDto toProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getReference(),
                product.getName(),
                product.getDescription(),
                product.getImage(),
                product.getData(),
                product.getPrice(),
                product.getDiscount(),
                product.getStock(),
                CalendarConversor.calendarToString(product.getCreateDate()),
                product.getTaxType().getId(),
                product.getTaxType().getValue(),
                product.getCategory().getId(),
                product.getCreator().getId());
    }

    public static List<ProductDto> toProductDto(List<Product> products) {

        return products.stream().map(ProductConversor::toProductDto).collect(Collectors.toList());
    }
}
