package es.udc.tfg.app.model.Note;

import es.udc.tfg.app.model.Client.Client;
import es.udc.tfg.app.model.Invoice.Invoice;
import es.udc.tfg.app.model.Noteline.Noteline;
import es.udc.tfg.app.model.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float subtotal = 0.0f;
    private Float taxes = 0.0f;
    private Float total = 0.0f;
    private String comment;

    @CreationTimestamp
    private Calendar createDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Noteline> notelines = new ArrayList<>();

    public Note() {
    }

    public Note(String comment, Client client, User creator) {
        this.comment = comment;
        this.client = client;
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public Float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Float subtotal) {
        this.subtotal = subtotal;
    }

    public Float getTaxes() {
        return taxes;
    }

    public void setTaxes(Float taxes) {
        this.taxes = taxes;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public List<Noteline> getNotelines() {
        return notelines;
    }

    public void setNotelines(List<Noteline> notelines) {
        this.notelines = notelines;
    }
}
