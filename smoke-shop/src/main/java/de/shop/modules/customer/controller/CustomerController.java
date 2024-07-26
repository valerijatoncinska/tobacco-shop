package de.shop.modules.customer.controller;

import de.shop.core.components.ResponseDto;
import de.shop.modules.customer.domain.CustomerDto;
import de.shop.modules.customer.service.CustomerService;
import de.shop.modules.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public class CustomerController implements CustomerService {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @Override
    public ResponseDto<CustomerDto> save(CustomerDto dto) {
        return null;
    }

    @Override
    public ResponseDto<CustomerDto> getById(Long id) {
        return null;
    }

    @Override
    public ResponseDto<List<CustomerDto>> getAllActiveCustomers() {
        return null;
    }

    @Override
    public ResponseDto<CustomerDto> getActiveCustomerById(Long id) {
        return null;
    }

    @Override
    public ResponseDto<CustomerDto> update(CustomerDto dto) {
        return null;
    }

    @Override
    public ResponseDto<CustomerDto> deleteById(Long id) {
        return null;
    }

    @Override
    public ResponseDto<CustomerDto> deleteByName(String name) {
        return null;
    }

    @Override
    public ResponseDto<CustomerDto> restoreById(Long id) {
        return null;
    }

    @Override
    public ResponseDto<Integer> getActiveCustomersNumber() {
        return null;
    }

    @Override
    public ResponseDto<BigDecimal> getCartTotalCost(Long customerId) {
        return service.getCartTotalCost(customerId);
    }

    @Override
    public ResponseDto<ProductDto> addProductToCustomersCart(Long customerId, Long productId) {
        return service.addProductToCustomersCart(customerId, productId);
    }

    @Override
    public ResponseDto<String> removeProductFromCustomersCart(Long customerId, Long productId) {
        return service.removeProductFromCustomersCart(customerId, productId);
    }

    @Override
    public ResponseDto<String> clearCart(Long customerId) {
        return service.clearCart(customerId);
    }
}
