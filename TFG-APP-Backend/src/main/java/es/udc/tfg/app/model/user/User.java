package es.udc.tfg.app.model.user;

import es.udc.tfg.app.util.enums.Languages;
import es.udc.tfg.app.util.enums.UserRole;

import java.util.Calendar;

public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private String dni;

    private String encryptedPassword;

    private String email;

    private Calendar birthDate;

    private Calendar createDate;

    private Languages language;

    private UserRole role;

    private String image;

    private boolean isActive;

    // ....... Constructores ......./

    public User() {
    }

    public User(String firstName, String lastName, String dni, String email, Calendar birthDate, Languages language, UserRole role, String image, boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
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
