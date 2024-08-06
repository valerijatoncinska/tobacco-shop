package de.shop.modules.product.controller;

import de.shop.modules.product.domain.dto.OutputProductAdminDto;
import de.shop.modules.product.service.ProductService;
import de.shop.modules.product.service.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class ProductController {

    private final ProductService service;
private ProductServiceImpl serviceImpl;
    public ProductController(ProductService service,ProductServiceImpl serviceImpl) {
        this.service = service;
        this.serviceImpl = serviceImpl;
    }
// Добавляем в корзину продукт.
    @PostMapping("/{id}/add")
    public ResponseEntity<?> addToCart(@PathVariable Long id) {
        boolean n = serviceImpl.addItemCart(id);
if (n==true) {
    return ResponseEntity.ok("ok");
}
else {
    return ResponseEntity.status(HttpStatus.CONFLICT).build();
}
    }
    @GetMapping("/{id}")

    public OutputProductAdminDto findByIdForUser(@PathVariable Long id) {
        return service.findByIdForUser(id);
    }

    @GetMapping
    public List<OutputProductAdminDto> findAllActiveProductsForUsers() {
        return service.findAllActiveProductsForUsers();
    }

}
