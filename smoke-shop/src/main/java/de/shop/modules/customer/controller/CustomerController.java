package de.shop.modules.customer.controller;

import de.shop.core.components.ResponseDto;
import de.shop.modules.customer.domain.CustomerDto;
import de.shop.modules.customer.service.CustomerService;
import de.shop.modules.order.domain.dto.OrderDto;
import de.shop.modules.product.domain.dto.ProductDto;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
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

    @GetMapping("/{id}")
    public ResponseDto<CustomerDto> getById(@PathVariable Long id) {

        return service.getById(id);
    }

    @GetMapping
    public ResponseDto<List<CustomerDto>> getAllCustomers() {

        return service.getAllActiveCustomers();
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

    @PutMapping("/{id}/restoration")
    public ResponseDto<CustomerDto> restoreById(@PathVariable Long id) {
        return service.restoreById(id);
    }

    @GetMapping("/count")
    public ResponseDto<Integer> getActiveCustomersNumber() {
        return service.getActiveCustomersNumber();
    }

    @GetMapping("/{id}/cart-total-cost")
    public ResponseDto<BigDecimal> getCartTotalCost(@PathVariable Long id) {
        return service.getCartTotalCost(id);
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

    @GetMapping("/{customerId}/cart")
    public ResponseDto<List<ProductDto>> getCart(@PathVariable Long customerId) {
        return service.getCart(customerId);
    }

    @GetMapping("/{customerId}/order-history")
    public ResponseDto<List<OrderDto>> getOrderHistory(@PathVariable Long customerId) {
        return service.getOrderHistory(customerId);
    }

    @PostMapping("/{customerId}/order-history-addition/{orderId}")
    public ResponseDto<OrderDto> addOrderToHistory(@PathVariable Long customerId, @PathVariable Long orderId) {
        return service.addOrderToHistory(customerId, orderId);
    }

    @DeleteMapping("/{customerId}/order-history-removal/{orderId}")
    public ResponseDto<String> removeOrderFromHistory(@PathVariable Long customerId, @PathVariable Long orderId) {
        return service.removeOrderFromHistory(customerId, orderId);
    }

    @DeleteMapping("/{customerId}/history-clearance")
    public ResponseDto<String> clearOrderHistory(@PathVariable Long customerId) {
        return service.clearOrderHistory(customerId);
    }
}
