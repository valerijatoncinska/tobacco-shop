package de.shop.modules.users.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "street")
    private String street;
    @Column(name = "house")
    private String house;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "locality")
    private String locality;
    @Column(name = "region")
    private String region;
    @Column(name = "country")
    private String country = "Deutschland";
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUserEntity(UserEntity user) {
        this.userEntity = user;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

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

    public String getCountry() {
        return country;
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
