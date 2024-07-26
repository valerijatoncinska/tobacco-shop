package de.shop.modules.customer.service;

import de.shop.core.components.ResponseDto;
import de.shop.modules.customer.domain.CustomerDto;

import java.util.List;

public interface CustomerService {
    ResponseDto<CustomerDto> save(CustomerDto dto);
    ResponseDto<CustomerDto> getById(Long id);
    ResponseDto<List<CustomerDto>> getAllActiveCustomers();
    ResponseDto<CustomerDto> getActiveCustomerById(Long id);
    ResponseDto<CustomerDto> update(CustomerDto dto);
    ResponseDto<CustomerDto> deleteById(Long id);
    ResponseDto<CustomerDto> deleteByName(String name);
    ResponseDto<CustomerDto> restoreById(Long id);
    ResponseDto<Integer> getActiveCustomersNumber();
    ResponseDto<CustomerDto> getCartTotalCost(Long customerId);
    ResponseDto<CustomerDto> getAverageProductCost(Long customerId);
    ResponseDto<CustomerDto> addProductToCustomersCart(Long customerId, Long productId);
    ResponseDto<Boolean> removeProductFromCustomersCart(Long customerId, Long productId);
    ResponseDto<CustomerDto> clearCart(Long customerId);
}
