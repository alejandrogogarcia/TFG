package es.udc.tfg.app.service.clientService;

public class ClientData {

    private String firstName;

    private String lastName;

    private String dni;

    private String address;

    private String city;

    private Long postCode;

    private String email;

    private Long phoneNumber;

    public ClientData() {
    }

    public ClientData(String firstName, String lastName, String dni, String address, String city, Long postCode, String email, Long phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.address = address;
        this.city = city;
        this.postCode = postCode;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getPostCode() {
        return postCode;
    }

    public void setPostCode(Long postCode) {
        this.postCode = postCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
