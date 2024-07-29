package de.shop.modules.address.domain;

import de.shop.modules.customer.domain.CustomerEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "address")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "house_number")
    private int houseNumber;

    @Column(name = "postal_code")
    private String postalCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
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
        AddressEntity that = (AddressEntity) o;
        return houseNumber == that.houseNumber && Objects.equals(id, that.id) && Objects.equals(customer, that.customer) && Objects.equals(street, that.street) && Objects.equals(city, that.city) && Objects.equals(postalCode, that.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, street, city, houseNumber, postalCode);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", customer=" + customer +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", houseNumber=" + houseNumber +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }

//    методы адреса, когда будет заготовка директорий и классов Order
//    ResponseDto<AddressDto> getAddressById(Long customerId);
//    ResponseDto<AddressDto> getAddressByPostalCode(Long customerPostalCode);
//    ResponseDto<List<AddressDto>> getAllCustomersByRegion(Long customerPostalCode);
//    ResponseDto<List<AddressDto>> getAllCustomersByCity(Long customerPostalCode);
//    ResponseDto<AddressDto> updateAddress(Long customerPostalCode);
//    ResponseDto<List<AddressDto>> deleteAddress(Long customerPostalCode);
}
