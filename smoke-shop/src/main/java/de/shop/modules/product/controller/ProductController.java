package de.shop.modules.product.controller;

import de.shop.core.components.ResponseDto;
import de.shop.modules.product.domain.dto.ProductDto;
import de.shop.modules.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ProductDto findByIdForUser(@PathVariable (required = false) Long id) {
        return service.findByIdForUser(id);
    }

    @GetMapping
    public List<ProductDto> findAllActiveProductsForUsers() {
        return service.findAllActiveProductsForUsers();
    }

}
