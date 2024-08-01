package de.shop.modules.order.controller;

import de.shop.core.components.ResponseDto;
import de.shop.modules.order.domain.dto.OrderDto;
import de.shop.modules.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseDto<OrderDto> save(@RequestBody OrderDto order) {
        return service.save(order);
    }

    @DeleteMapping
    public ResponseDto<OrderDto> deleteById(@RequestParam Long id) {
        return service.deleteById(id);
    }

    @PutMapping
    public ResponseDto<OrderDto> update(OrderDto order) {
        return service.update(order);
    }

    @GetMapping
    public ResponseDto<OrderDto> findById(@RequestParam Long id) {
        return service.findById(id);
    }

    @GetMapping("/all")
    public ResponseDto<List<OrderDto>> findAllActiveProducts() {
        return service.findAllOrders();
    }
}
