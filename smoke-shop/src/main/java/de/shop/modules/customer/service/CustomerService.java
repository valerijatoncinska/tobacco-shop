package de.shop.modules.customer.service;

import de.shop.core.components.ResponseDto;
import de.shop.modules.customer.domain.CustomerDto;

import de.shop.modules.product.domain.dto.ProductDto;

import java.math.BigDecimal;

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
    ResponseDto<BigDecimal> getCartTotalCost(Long customerId);
    ResponseDto<ProductDto> addProductToCustomersCart(Long customerId, Long productId);
    ResponseDto<String> removeProductFromCustomersCart(Long customerId, Long productId);
    ResponseDto<String> clearCart(Long customerId);
}
