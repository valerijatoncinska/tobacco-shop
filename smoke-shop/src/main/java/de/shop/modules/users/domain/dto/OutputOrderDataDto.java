package de.shop.modules.users.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OutputOrderDataDto {
    private Long id;
    private BigDecimal total;
    private String payments;
    private String deliveryAddress;
    private String billingAddress;
    private String orderStatus;
    private LocalDateTime date;
    private String email;
    private String phone;

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


    public OutputOrderDataDto(Long id, String payments, String deliveryAddress, String billingAddress, LocalDateTime date, BigDecimal total, String orderStatus) {
        this.payments = payments;
        this.deliveryAddress = deliveryAddress;
        this.billingAddress = billingAddress;
        this.id = id;
        this.date = date;
        this.total = total;
        this.orderStatus = orderStatus;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setOrderStatus(String status) {
        this.orderStatus = status;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public OutputOrderDataDto() {

    }

    public void setPayments(String payments) {
        this.payments = payments;
    }

    public String getPayments() {
        return payments;
    }

    public void setDeliveryAddress(String address) {
        this.deliveryAddress = address;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setBillingAddress(String address) {
        this.billingAddress = address;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

}
