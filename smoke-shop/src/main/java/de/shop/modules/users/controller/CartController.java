package de.shop.modules.users.controller;

import de.shop.core.exceptions.UserSearchException;
import de.shop.modules.users.domain.dto.CartDto;
import de.shop.modules.users.domain.dto.InputCartQuantityDto;
import de.shop.modules.users.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@Secured("ROLE_USER")
public class CartController {
    private CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartDto> cartQuantity(@PathVariable Long id, @RequestBody InputCartQuantityDto dto) {
return ResponseEntity.ok(service.cartQuantity(id,dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (service.drop(id) == true) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public List<CartDto> cartItemList() {
        return service.list();
    }
}
