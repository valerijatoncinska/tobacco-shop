package de.shop.modules.address.domain;

import de.shop.modules.customer.domain.CustomerEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Table(name = "address")
public class AddressDto {

    private Long id;

    private String region;

    private String city;

    private String street;

    private int houseNumber;

    private String postalCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDto that = (AddressDto) o;
        return houseNumber == that.houseNumber && Objects.equals(id, that.id) && Objects.equals(region, that.region) && Objects.equals(street, that.street) && Objects.equals(city, that.city) && Objects.equals(postalCode, that.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, region, street, city, houseNumber, postalCode);
    }

    @Override
    public String toString() {
        return "AddressDto{" +
                "id=" + id +
                ", region='" + region + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", houseNumber=" + houseNumber +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
