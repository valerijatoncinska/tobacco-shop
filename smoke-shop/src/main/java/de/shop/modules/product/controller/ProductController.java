package de.shop.modules.product.controller;

import de.shop.modules.product.domain.dto.OutputDto;
import de.shop.modules.product.domain.dto.OutputProductAdminDto;
import de.shop.modules.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // Добавляем в корзину продукт.
    @PostMapping("/{id}/addition-to-cart")
@Secured({"ROLE_USER", "ROLE_ADMIN"})

    public ResponseEntity<?> addToCart(@PathVariable Long id) {
        boolean n = service.addItemCart(id);
        if (n == true) {
            return ResponseEntity.ok("ok");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // @GetMapping("/{id}")
    // public OutputProductAdminDto findByIdForUser(@PathVariable Long id) {
        // return service.findByIdForUser(id);
    // }

    @GetMapping
    public ResponseEntity<OutputDto> productsAll() {
        return ResponseEntity.ok(service.productsAll());
    }

}
