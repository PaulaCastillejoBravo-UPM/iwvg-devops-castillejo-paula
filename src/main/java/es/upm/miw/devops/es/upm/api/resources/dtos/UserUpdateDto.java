package es.upm.miw.devops.es.upm.api.resources.dtos;

import es.upm.miw.devops.es.upm.api.infrastructure.data.models.Role;

public class UserUpdateDto {

    private String firstName;
    private String familyName;
    private String email;
    private String mobile;
    private String identity;
    private String address;
    private String city;
    private Integer postalCode;
    private String province;
    private String password;
    private Boolean active;
    private Role role;

    public UserUpdateDto(
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

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getIdentity() {
        return identity;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public String getProvince() {
        return province;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getActive() {
        return active;
    }

    public Role getRole() {
        return role;
    }
}
