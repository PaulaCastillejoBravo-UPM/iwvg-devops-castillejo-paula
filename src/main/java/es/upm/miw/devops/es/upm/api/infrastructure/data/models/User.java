package es.upm.miw.devops.es.upm.api.infrastructure.data.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    private UUID id;

    private String firstName;
    private String familyName;
    private String mobile;
    private String email;
    private String identity;
    private String address;
    private String city;

    private Integer postalCode;
    private String province;
    private String password;

    private Boolean active;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(
            UUID id,
            String firstName,
            String familyName,
            String email,
            String mobile,
            String identity,
            String address,
            String city,
            Integer postalCode,
            String province,
            String password,
            Boolean active,
            Role role
    ) {
        this.id = id;

        this.firstName = firstName;
        this.familyName = familyName;
        this.email = email;
        this.mobile = mobile;
        this.identity = identity;
        this.address = address;
        this.city = city;

        this.postalCode = postalCode;
        this.province = province;
        this.password = password;

        this.active = active;

        this.role = role;
    }

    public UUID getId() { return id; }

    public String getFirstName() { return firstName; }

    public String getFamilyName() { return familyName; }

    public String getMobile() { return mobile; }

    public String getEmail() { return email; }

    public String getIdentity() { return identity; }

    public String getAddress() { return address; }

    public String getCity() { return city; }

    public Integer getPostalCode() { return postalCode; }

    public String getProvince() { return province; }

    public String getPassword() { return password; }

    public Boolean getActive() { return active; }

    public Role getRole() { return role; }
}
