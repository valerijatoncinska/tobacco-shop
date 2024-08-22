package de.shop.modules.users.controller;

import de.shop.modules.users.domain.dto.CartDto;
import de.shop.modules.users.domain.dto.InputCartQuantityDto;
import de.shop.modules.users.domain.dto.InputOrderDto;
import de.shop.modules.users.domain.dto.OutputCartDto;
import de.shop.modules.users.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
@Secured("ROLE_USER")
public class CartController {
    private CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @PostMapping("/order")
    public ResponseEntity<?> order(@RequestBody InputOrderDto dto) {
        return ResponseEntity.ok(service.order(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartDto> cartQuantity(@PathVariable Long id, @RequestBody InputCartQuantityDto dto) {
        return ResponseEntity.ok(service.cartQuantity(id, dto));
    }

    @DeleteMapping("/clear")
    public Object clear() {
        if (service.clear() == true) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable Long id) {
        if (service.drop(id) == true) {
            return id;
            // status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public OutputCartDto cartItemList() {
        return service.list();
    }
}
