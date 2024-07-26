package de.shop.modules.customer.controller;

import de.shop.core.components.ResponseDto;
import de.shop.modules.customer.domain.CustomerDto;
import de.shop.modules.customer.service.CustomerService;

import java.util.List;

public class CustomerController implements CustomerService {
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
    public ResponseDto<CustomerDto> getCartTotalCost(Long customerId) {
        return null;
    }

    @Override
    public ResponseDto<CustomerDto> getAverageProductCost(Long customerId) {
        return null;
    }

    @Override
    public ResponseDto<CustomerDto> addProductToCustomersCart(Long customerId, Long productId) {
        return null;
    }

    @Override
    public ResponseDto<Boolean> removeProductFromCustomersCart(Long customerId, Long productId) {
        return null;
    }

    @Override
    public ResponseDto<CustomerDto> clearCart(Long customerId) {
        return null;
    }
}
