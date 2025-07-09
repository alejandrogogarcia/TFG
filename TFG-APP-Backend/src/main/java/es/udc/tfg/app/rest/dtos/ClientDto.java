package es.udc.tfg.app.rest.dtos;

public class ClientDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String dni;

    private String address;

    private String city;

    private Long postCode;

    private String email;

    private Long phoneNumber;

    private String createDate;

    private Long creator_id;

    public ClientDto() {
    }

    public ClientDto(Long id, String firstName, String lastName, String dni, String address, String city,
                     Long postCode, String email, Long phoneNumber, String createDate, Long creator_id) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.address = address;
        this.city = city;
        this.postCode = postCode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.creator_id = creator_id;
    }

    // Getters y Setters

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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Long getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(Long creator_id) {
        this.creator_id = creator_id;
    }
}
