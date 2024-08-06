package de.shop.modules.users.controller;

import de.shop.core.exceptions.UserSearchException;
import de.shop.modules.users.domain.dto.CartDto;
import de.shop.modules.users.service.CartService;
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

    @DeleteMapping("/{id}/deletion")
    public void delete(@PathVariable Long id) throws UserSearchException {
        service.drop(id);

    }

    @GetMapping
    public List<CartDto> cartItemList() {
        return service.list();
    }
}
