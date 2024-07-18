package es.udc.tfg.app.service.userservice;

public class RegisterData {

    private String firstname;

    private String lastName;

    private String dni;

    private String email;

    private String password;

    private String birthDate;

    private String language;

    private String role;

    private String image;

    public RegisterData() {
    }

    public RegisterData(String firstname, String lastName, String dni, String email, String password, String birthDate,
                        String language, String role, String image) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.dni = dni;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.language = language;
        this.role = role;
        this.image = image;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}