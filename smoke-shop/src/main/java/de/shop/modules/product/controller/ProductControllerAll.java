package de.shop.modules.product.controller;

import de.shop.modules.product.domain.dto.OutputProductAdminDto;
import de.shop.modules.product.service.ProductService;
import de.shop.modules.product.service.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductControllerAll {

    private final ProductService service;
    private ProductServiceImpl serviceImpl;

    public ProductControllerAll(ProductService service, ProductServiceImpl serviceImpl) {
        this.service = service;
        this.serviceImpl = serviceImpl;
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
