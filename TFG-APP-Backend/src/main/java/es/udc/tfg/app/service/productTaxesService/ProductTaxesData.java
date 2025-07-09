package es.udc.tfg.app.service.productTaxesService;

public class ProductTaxesData {

    private String type;

    private String description;

    private Float value;

    public ProductTaxesData() {}

    public ProductTaxesData(String type, String description, Float value) {
        this.type = type;
        this.description = description;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
