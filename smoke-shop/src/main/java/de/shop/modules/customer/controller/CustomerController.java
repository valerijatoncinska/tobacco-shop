package de.shop.modules.customer.controller;

import de.shop.core.components.ResponseDto;
import de.shop.modules.customer.domain.CustomerDto;
import de.shop.modules.customer.service.CustomerService;
import de.shop.modules.product.domain.dto.ProductDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseDto<CustomerDto> save(@RequestBody CustomerDto dto) {
        return service.save(dto);
    }

    @GetMapping()
    public ResponseDto<?> getById(@RequestParam(required = false) Long id
    ) {
        if (id == null) {
            return service.getAllActiveCustomers();
        } else {
            return service.getActiveCustomerById(id);
        }

    }

    @PutMapping
    public ResponseDto<CustomerDto> update(@RequestBody CustomerDto dto) {
        return service.update(dto);
    }

    @PutMapping("/{id}/status")
    public ResponseDto<CustomerDto> deleteCustomer(@PathVariable Long id) {
        return service.deleteById(id);
    }


//    public ResponseDto<CustomerDto> deleteByName(@RequestParam String name) {
//        return null;
//    }

    @PutMapping("{id}/restoration")
    public ResponseDto<CustomerDto> restoreById(@PathVariable Long id) {
        return service.restoreById(id);
    }

    @GetMapping("/count")
    public ResponseDto<Integer> getActiveCustomersNumber() {
        return service.getActiveCustomersNumber();
    }

    @GetMapping("/cart-total-cost")
    public ResponseDto<BigDecimal> getCartTotalCost(@RequestParam Long customerId) {
        return service.getCartTotalCost(customerId);
    }

    @PutMapping("/{customerId}/cart-product-addition/{productId}")
    public ResponseDto<ProductDto> addProductToCustomersCart(
            @PathVariable Long customerId,
            @PathVariable Long productId) {
        return service.addProductToCustomersCart(customerId, productId);
    }

    @DeleteMapping("/{customerId}/cart-product-removal/{productId}")
    public ResponseDto<String> removeProductFromCustomersCart(
            @PathVariable Long customerId,
            @PathVariable Long productId) {
        return service.removeProductFromCustomersCart(customerId, productId);
    }

    @DeleteMapping("/{customerId}/cart-clearance")
    public ResponseDto<String> clearCart(@PathVariable Long customerId) {
        return service.clearCart(customerId);
    }
}
