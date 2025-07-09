package es.udc.tfg.app.rest.dtos;

import java.util.Calendar;

public class ProductDto {

    private Long id;

    private String reference;

    private String name;

    private String description;

    private String image;

    private String data;

    private Float price;

    private Integer discount;

    private Integer stock;

    private String createData;

    private Long taxTypeId;

    private Float taxValue;

    private Long categoryId;

    private Long creator;

    public ProductDto() {
    }

    public ProductDto(Long id, String reference, String name, String description, String image, String data, Float price, Integer discount, Integer stock, String createData, Long taxTypeId, Float taxValue, Long categoryId, Long creator) {
        this.id = id;
        this.reference = reference;
        this.name = name;
        this.description = description;
        this.image = image;
        this.data = data;
        this.price = price;
        this.discount = discount;
        this.stock = stock;
        this.createData = createData;
        this.taxTypeId = taxTypeId;
        this.taxValue = taxValue;
        this.categoryId = categoryId;
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCreateData() {
        return createData;
    }

    public void setCreateData(String createData) {
        this.createData = createData;
    }

    public Long getTaxTypeId() {
        return taxTypeId;
    }

    public void setTaxTypeId(Long taxTypeId) {
        this.taxTypeId = taxTypeId;
    }

    public Float getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(Float taxValue) {
        this.taxValue = taxValue;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }
}
