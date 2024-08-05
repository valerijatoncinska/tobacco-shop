package de.shop.modules.product.service;

import de.shop.core.components.LanguageResolver;
import de.shop.core.components.ResponseDto;
import de.shop.core.exceptions.ProductNotFoundException;
import de.shop.core.exceptions.ProductNotSavedException;
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

    public ProductServiceImpl(ProductRepository repository, ProductMappingService mappingService, LanguageResolver lang) {
        this.repository = repository;
        this.mappingService = mappingService;
        this.lang = lang;
    }

    @Override
    public ProductEntity save(ProductEntity product) {
        Properties p = lang.load("product", "messages");

        try {
            repository.save(product);
        } catch (Exception e) {
            throw new ProductNotSavedException(((String) p.get("product_not_saved")));
        }
        return product;
    }

    @Override
    public void archiveById(Long id) {
        Properties p = lang.load("product", "messages");
        ProductEntity entity = repository.findById(id).orElseThrow( () -> new ProductNotFoundException(((String) p.get("product_not_found"))));
        if (entity.isActive()) {
            entity.setActive(false);
        } else {
            throw new ProductNotFoundException(((String) p.get("product_already_archived")));
        }
    }

    @Override
    public ProductEntity restoreById(Long id) {
        Properties p = lang.load("product", "messages");
        ProductEntity entity = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(((String) p.get("product_not_found_by_id"))));

        entity.setActive(true);

        return entity;
    }

    @Override
    public ProductEntity update(ProductEntity entity) {
        Properties p = lang.load("product", "messages");

        Long id = entity.getId();
        ProductEntity oldEntity = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(((String) p.get("product_not_found_by_id"))));

        oldEntity.setTitle(entity.getTitle());
        oldEntity.setPrice(entity.getPrice());
        oldEntity.setActive(entity.isActive());
        oldEntity.setQuantity(entity.getQuantity());

        return oldEntity;
    }

    @Override
    public ProductDto findByIdForUser(Long id) {
        Properties p = lang.load("product", "messages");

        ProductEntity entity = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(((String) p.get("product_not_found_by_id"))));
        if (!entity.isActive()) {
            throw new ProductNotFoundException(((String) p.get("product_not_active")));
        } else {
            ProductDto dto = mappingService.mapEntityToDto(entity);
            return dto;
        }
    }

    @Override
    public ProductEntity findByIdForAdmin(Long id) {
        Properties p = lang.load("product", "messages");

        ProductEntity entity = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(((String) p.get("product_not_found_by_id"))));
        if (!entity.isActive()) {
            throw new ProductNotFoundException(((String) p.get("product_not_found_by_id")));
        }

        return entity;
    }

    @Override
    public List<ProductDto> findAllActiveProductsForUsers() {
        List<ProductDto> foundProducts = repository.findAll()
                .stream()
                .filter(ProductEntity::isActive)
                .map(mappingService::mapEntityToDto)
                .toList();
        return foundProducts;
    }

    @Override
    public List<ProductEntity> findAllActiveProductsForAdmin() {
        return repository.findAll()
                .stream()
                .filter(ProductEntity::isActive)
                .toList();
    }
}
