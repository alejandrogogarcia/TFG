package es.udc.tfg.app.service.companyInfoService;

import jakarta.persistence.*;

public class CompanyInfoData {

        private String name;
        private String address;
        private Long postCode;
        private String nif;
        private String email;
        private String web;
        private String logo;

    public CompanyInfoData() {
    }

    public CompanyInfoData(String name, String address, Long postCode, String nif, String email, String web, String logo) {
        this.name = name;
        this.address = address;
        this.postCode = postCode;
        this.nif = nif;
        this.email = email;
        this.web = web;
        this.logo = logo;
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