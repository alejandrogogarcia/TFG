package es.udc.tfg.app.util.conversors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductMediaHolder {

    private static String productMediaPath;

    @Value("${app.product.path.final}")
    public void setProductMediaPath(String path) {
        ProductMediaHolder.productMediaPath = path;
    }

    public static String getProductMediaPath() {
        return productMediaPath;
    }
}
