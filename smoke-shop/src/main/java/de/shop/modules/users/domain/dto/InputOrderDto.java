package de.shop.modules.users.domain.dto;

public class InputOrderDto {
    private String payments;
    private Long deliveryAddress;
    private Long billingAddress;

    public InputOrderDto(String payments, Long deliveryAddress, Long billingAddress) {
        this.payments = payments;
        this.deliveryAddress = deliveryAddress;
        this.billingAddress = billingAddress;
    }

    public InputOrderDto() {

    }

    public void setPayments(String payments) {
        this.payments = payments;
    }

    public String getPayments() {
        return payments;
    }

    public void getDeliveryAddress(Long address) {
        this.deliveryAddress = address;
    }

    public Long getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setBillingAddress(Long address) {
        this.billingAddress = address;
    }

    public Long getBillingAddress() {
        return billingAddress;
    }

}
