package es.udc.tfg.app.util.conversors;

import es.udc.tfg.app.model.Product.Product;
import es.udc.tfg.app.rest.dtos.ProductDto;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class ProductConversor {

    public static ProductDto toProductDto(Product product) {
        String image = null;
        String data = null;
        String productMediaPath = ProductMediaHolder.getProductMediaPath();

        try {
            if (product.getImage() != null) {
                System.out.println(productMediaPath);
                Path imagePath = Path.of(productMediaPath, product.getImage());
                System.out.println(imagePath.toString());
                if (Files.exists(imagePath)) {
                    String mimeType = Files.probeContentType(imagePath);
                    byte[] imageBytes = Files.readAllBytes(imagePath);
                    image = "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(imageBytes);
                }
            }

            if (product.getData() != null) {
                Path dataPath = Path.of(productMediaPath, product.getData());
                System.out.println(dataPath.toString());
                if (Files.exists(dataPath)) {
                    String mimeType = Files.probeContentType(dataPath);
                    byte[] dataBytes = Files.readAllBytes(dataPath);
                    data = "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(dataBytes);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler ficheiros de produto: " + e.getMessage());
        }
        return new ProductDto(
                product.getId(),
                product.getReference(),
                product.getName(),
                product.getDescription(),
                image,
                data,
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
