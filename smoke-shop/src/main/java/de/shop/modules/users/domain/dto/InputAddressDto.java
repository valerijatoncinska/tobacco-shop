package de.shop.modules.users.domain.dto;

import de.shop.modules.users.domain.entity.UserEntity;
import jakarta.persistence.*;

public class InputAddressDto {
    private Long id;
    private String street;
    private String house;
    private String postalCode;
    private String locality;
    private String region;
    private String email;
    private String phone;

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getHouse() {
        return house;
    }

    public void setPostalCode(String code) {
        this.postalCode = code;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getLocality() {
        return locality;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }
public void setEmail(String email) {
        this.email = email;
}
public String getEmail() {
       return email;
}
public void setPhone(String phone) {
        this.phone = phone;
}
public String getPhone() {
        return phone;
}



}
