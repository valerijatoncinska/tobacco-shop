package de.shop.modules.product.controller;

import de.shop.core.components.ResponseDto;
import de.shop.modules.product.domain.dto.ProductDto;
import de.shop.modules.product.domain.entity.ProductEntity;
import de.shop.modules.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/products")
public class ProductControllerAdmin {

    private final ProductService service;

    public ProductControllerAdmin(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ProductEntity save(@RequestBody ProductEntity product) {
        return service.save(product);
    }

    @PutMapping("{id}")
    public void archiveById(@PathVariable Long id) {

    }

    @PutMapping("/{id}/restore")
    public ProductEntity restoreById(@PathVariable Long id) {
        return service.restoreById(id);
    }

    @PutMapping("/modifications")
    public ProductEntity update(ProductEntity product) {
        return service.update(product);
    }

    @GetMapping("/{id}")
    public ProductEntity findById(@PathVariable Long id) {
        return service.findByIdForAdmin(id);
    }

    @GetMapping
    public List<ProductEntity> findAllActiveProductsForAdmin() {
        return service.findAllActiveProductsForAdmin();
    }

}
