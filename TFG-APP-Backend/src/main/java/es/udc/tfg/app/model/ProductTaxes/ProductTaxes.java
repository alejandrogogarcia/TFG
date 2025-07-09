package es.udc.tfg.app.model.ProductTaxes;

import jakarta.persistence.*;

@Entity
@Table(name = "product_taxes")
public class ProductTaxes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String description;

    private Float value;

    public ProductTaxes() {}

    public ProductTaxes(String type, String description, Float value) {
        this.type = type;
        this.description = description;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
