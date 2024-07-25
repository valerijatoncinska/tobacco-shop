package de.shop.modules.product.service;

import de.shop.core.components.LanguageResolver;
import de.shop.core.components.ResponseDto;
import de.shop.modules.product.domain.dto.ProductDto;
import de.shop.modules.product.domain.entity.ProductEntity;
import de.shop.modules.product.repository.interfaces.ProductRepository;
import de.shop.modules.product.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMappingService mappingService;
    private LanguageResolver lang;

    public ProductServiceImpl(ProductRepository repository, ProductMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    public ResponseDto<ProductDto> save(ProductDto product) {
        return null;
    }

    @Override
    public ResponseDto<Boolean> deleteById(Long id) {
        return null;
    }

    @Override
    public ResponseDto<Boolean> restoreById(Long id) {
        return null;
    }

    @Override
    public ResponseDto<ProductDto> update(ProductDto product) {
        return null;
    }

    @Override
    public ResponseDto<ProductDto> findById(Long id) {
        Properties p = lang.load("product", "messages.properties");
        ProductEntity product = repository.findById(id).orElse(null);
        if (product == null || !product.isActive()) {
            return new ResponseDto<>(false, null, p.get("product_not_found_by_id").toString(), "eng");
        } else {
            ProductDto dto = mappingService.mapEntityToDto(product);
            return new ResponseDto<>(true, dto, p.get("product_found").toString(), "eng");
        }
    }

    @Override
    public ResponseDto<List<ProductDto>> findAllActiveProducts() {
        return null;
    }

}
