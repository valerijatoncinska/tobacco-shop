package de.shop.modules.product.controller;

import de.shop.core.components.ResponseDto;
import de.shop.modules.product.domain.dto.ProductDto;
import de.shop.modules.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseDto<ProductDto> save(@RequestBody ProductDto product) {
        return service.save(product);
    }

    @PutMapping()
    public ResponseDto<ProductDto> deleteById(@RequestParam Long id) {
        return service.deleteById(id);
    }

    @PutMapping("/{id}/restore")
    public ResponseDto<ProductDto> restoreById(@PathVariable Long id) {
        return service.restoreById(id);
    }

    @PutMapping("/modification")
    public ResponseDto<ProductDto> update(ProductDto product) {
        return service.update(product);
    }

    @GetMapping
    public ResponseDto<ProductDto> findById(@RequestParam Long id) {
        return service.findById(id);
    }

    @GetMapping("/all")
    public ResponseDto<List<ProductDto>> findAllActiveProducts() {
        return service.findAllActiveProducts();
    }
}
