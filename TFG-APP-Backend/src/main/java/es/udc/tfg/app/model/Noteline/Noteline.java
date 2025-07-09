package es.udc.tfg.app.model.Noteline;

import es.udc.tfg.app.model.Note.Note;
import es.udc.tfg.app.model.Product.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "notelines")
public class Noteline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "note_id")
    private Note note;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    private Float price;
    private Integer amount;
    private Integer discount;
    private Float taxes;
    private String comment;

    public Noteline() {
    }

    public Noteline(Note note, Product product, Float price, Integer amount, Integer discount, Float taxes, String comment) {
        this.note = note;
        this.product = product;
        this.price = price;
        this.amount = amount;
        this.discount = discount;
        this.taxes = taxes;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Float getTaxes() {
        return taxes;
    }

    public void setTaxes(Float taxes) {
        this.taxes = taxes;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
