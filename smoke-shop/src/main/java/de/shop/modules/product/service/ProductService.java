package de.shop.modules.product.service;

import de.shop.modules.product.domain.dto.InputProductDto;
import de.shop.modules.product.domain.dto.OutputProductAdminDto;
import de.shop.modules.product.domain.entity.ProductEntity;

import java.util.List;

public interface ProductService {

    OutputProductAdminDto save(InputProductDto product);

    void archiveById(Long id);

    ProductEntity restoreById(Long id);

    ProductEntity update(ProductEntity entity);

    OutputProductAdminDto findByIdForUser(Long id);

    ProductEntity findByIdForAdmin(Long id);

    List<OutputProductAdminDto> findAllActiveProductsForAdmin();

    List<OutputProductAdminDto> findAllActiveProductsForUsers();

}