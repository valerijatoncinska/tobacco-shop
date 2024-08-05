package de.shop.modules.product.service;

import de.shop.modules.product.domain.dto.ProductDto;
import de.shop.modules.product.domain.entity.ProductEntity;

import java.util.List;

public interface ProductService {

    ProductEntity save(ProductEntity product);

    void archiveById(Long id);

    ProductEntity restoreById(Long id);

    ProductEntity update(ProductEntity entity);

    ProductDto findByIdForUser(Long id);

    ProductEntity findByIdForAdmin(Long id);

    List<ProductEntity> findAllActiveProductsForAdmin();

    List<ProductDto> findAllActiveProductsForUsers();

}