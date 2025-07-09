package es.udc.tfg.app.model.Category;

import es.udc.tfg.app.model.user.User;
import jakarta.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Calendar createDate;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    public Category() {
    }

    public Category(String name, String description, User creator) {
        this.name = name;
        this.description = description;
        this.createDate = Calendar.getInstance();
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
