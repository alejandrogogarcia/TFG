package es.udc.tfg.app.model.CompanyInfo;

import jakarta.persistence.*;

@Entity
@Table(name = "company_info")
public class CompanyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private Long postCode;
    private String nif;
    private String email;
    private String web;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String logo;

    public CompanyInfo() {
    }

    public CompanyInfo(Long id, String name, String address, Long postCode, String nif, String email, String web, String logo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postCode = postCode;
        this.nif = nif;
        this.email = email;
        this.web = web;
        this.logo = logo;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPostCode() {
        return postCode;
    }

    public void setPostCode(Long postCode) {
        this.postCode = postCode;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
