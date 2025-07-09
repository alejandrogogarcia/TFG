package es.udc.tfg.app.model.Invoice;

import es.udc.tfg.app.model.Client.Client;
import es.udc.tfg.app.model.Note.Note;
import es.udc.tfg.app.model.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float subtotal = 0.0f;
    private Float taxes = 0.0f;
    private Float total = 0.0f;

    @CreationTimestamp
    private Calendar createDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();

    public Invoice() {
    }

    public Invoice(Float subtotal, Float taxes, Float total, Client client, User creator) {
        this.subtotal = subtotal;
        this.taxes = taxes;
        this.total = total;
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

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
