package de.shop.modules.users.controller;

import de.shop.modules.users.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер заказов пользователя
 */
@RestController
@RequestMapping("/orders")
@Secured("ROLE_USER") // доступно тем, кто имеет роль пользователя
public class OrderController {
    private OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> listOrders() {

        return ResponseEntity.ok(service.listOrders());

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> order(@PathVariable Long id) {
        return ResponseEntity.ok(service.info(id));
    }
}
