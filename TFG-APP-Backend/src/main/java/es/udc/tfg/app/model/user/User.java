package es.udc.tfg.app.model.user;

import es.udc.tfg.app.util.enums.Languages;
import es.udc.tfg.app.util.enums.UserRole;
import java.time.LocalDateTime;
import jakarta.persistence.*;

import java.util.Calendar;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expiryDate;

    private String firstName;

    private String lastName;

    private String dni;

    private String encryptedPassword;

    private String email;

    private Calendar birthDate;

    private Calendar createDate;

    @Column(columnDefinition = "ENUM('ESP', 'GAL', 'ENG')")
    @Enumerated(EnumType.STRING)
    private Languages language;

    @Column(columnDefinition = "ENUM('EMPLOYEE', 'CLERK', 'ADMIN')")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(columnDefinition = "LONGTEXT")
    private String image;

    private boolean isActive;

    // ....... Constructores ......./

    public User() {
    }

    public User(String firstName, String lastName, String dni, String encryptedPassword, String email, Calendar birthDate, Languages language, UserRole role, String image, boolean isActive) {
        this.token= TokenGenerator.generateToken();
        this.expiryDate = LocalDateTime.now().plusDays(3);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.encryptedPassword = encryptedPassword;
        this.email = email;
        this.birthDate = birthDate;
        this.createDate = Calendar.getInstance();
        this.language = language;
        this.role = role;
        this.image = image;
        this.isActive = isActive;
    }

    // ....... Getters & Setters ......./

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public Languages getLanguage() {
        return language;
    }

    public void setLanguage(Languages language) {
        this.language = language;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
