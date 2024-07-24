package de.shop.modules.product.service;

import de.shop.core.components.ResponseDto;
import de.shop.modules.product.domain.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ResponseDto<ProductDto> save(ProductDto product);

    ResponseDto<Boolean> deleteById(Long id);

    ResponseDto<Boolean> restoreById(Long id);

    ResponseDto<ProductDto> update(ProductDto product);

    ResponseDto<ProductDto> findById(Long id);

    ResponseDto<List<ProductDto>> findAllActiveProducts();

}
