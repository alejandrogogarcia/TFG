package es.udc.tfg.app.service.productService;


import java.util.Calendar;

public class ProductData {

    private String reference;

    private String name;

    private String description;

    private String image;

    private String data;

    private Float price;

    private Integer discount;

    private Integer stock;

    private Long taxTypeId;

    private Long categoryId;

    public ProductData() {
    }

    public ProductData(String reference, String name, String description, String image, String data, Float price, Integer discount, Integer stock, Long taxTypeId, Long categoryId) {
        this.reference = reference;
        this.name = name;
        this.description = description;
        this.image = image;
        this.data = data;
        this.price = price;
        this.discount = discount;
        this.stock = stock;
        this.taxTypeId = taxTypeId;
        this.categoryId = categoryId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getTaxTypeId() {
        return taxTypeId;
    }

    public void setTaxTypeId(Long taxTypeId) {
        this.taxTypeId = taxTypeId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
