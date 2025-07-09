package es.udc.tfg.app.model.Product;

import es.udc.tfg.app.model.Category.Category;
import es.udc.tfg.app.model.ProductTaxes.ProductTaxes;
import es.udc.tfg.app.model.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Calendar;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;

    private String name;

    private String description;

    private String image;

    private String data;

    private Float price;

    private Integer discount;

    private Integer stock;

    @CreationTimestamp
    private Calendar createDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tax_type_id")
    private ProductTaxes taxType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "creator_id")
    private User creator;

    public Product() {
    }

    public Product(String reference, String name, String description, String image, String data,
                   Float price, Integer discount, Integer stock, ProductTaxes taxType,
                   Category category, User creator) {
        this.reference = reference;
        this.name = name;
        this.description = description;
        this.image = image;
        this.data = data;
        this.price = price;
        this.discount = discount;
        this.stock = stock;
        this.taxType = taxType;
        this.category = category;
        this.creator = creator;
    }

    public Long getId() {
        return id;
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

    public Float getFinalPrice() {
        return (price * (1 - (discount / 100.0f)));
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

    public Calendar getCreateDate() {
        return createDate;
    }

    public ProductTaxes getTaxType() {
        return taxType;
    }

    public void setTaxType(ProductTaxes taxType) {
        this.taxType = taxType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}