package es.upm.miw.devops.es.upm.api.infrastructure.data.models;

import java.util.UUID;

public class User {

    private UUID id;

    private String firstName;
    private String familyName;
    private String mobile;
    private String email;
    private String address;
    private String city;

    private Integer postalCode;
    private String password;

    public User(
            UUID id,
            String firstName,
            String familyName,
            String email,
            String mobile,
            String address,
            String city,
            Integer postalCode,
            String password
    ) {
        this.id = id;

        this.firstName = firstName;
        this.familyName = familyName;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.city = city;

        this.postalCode = postalCode;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getFamilyName() {
        return familyName;
    }
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) { this.address = address; }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPostalCode() {
        return postalCode;
    }
    public void setMobile(Integer postalCode) { this.postalCode = postalCode; }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) { this.password = password; }

}
