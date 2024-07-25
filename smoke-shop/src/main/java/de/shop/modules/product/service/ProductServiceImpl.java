package de.shop.modules.product.service;

import de.shop.core.components.LanguageResolver;
import de.shop.core.components.ResponseDto;
import de.shop.modules.product.domain.dto.ProductDto;
import de.shop.modules.product.domain.entity.ProductEntity;
import de.shop.modules.product.repository.interfaces.ProductRepository;
import de.shop.modules.product.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMappingService mappingService;
    private final LanguageResolver lang;

    public ProductServiceImpl(ProductRepository repository, ProductMappingService mappingService, LanguageResolver lang) {
        this.repository = repository;
        this.mappingService = mappingService;
        this.lang = lang;
    }

    @Override
    public ResponseDto<ProductDto> save(ProductDto product) {
        Properties p = lang.load("product", "messages.properties");
        ProductEntity entity = mappingService.mapDtoToEntity(product);

        try {
            repository.save(entity);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, p.get("product_not_saved").toString(), lang.getCurrentLang());
        }
        mappingService.mapEntityToDto(entity);
        return new ResponseDto<>(true, product, p.get("product_saved").toString(), lang.getCurrentLang());
    }

    @Override
    public ResponseDto<ProductDto> deleteById(Long id) {
        Properties p = lang.load("product", "messages.properties");
        ProductDto dto = findById(id).getData();
        if (dto == null) {
            return new ResponseDto<>(false, null, p.get("product_not_found_by_id").toString(), lang.getCurrentLang());
        }
        ProductEntity entity = mappingService.mapDtoToEntity(dto);
        entity.setActive(false);
        dto = mappingService.mapEntityToDto(entity);

        return new ResponseDto<>(true, dto, p.get("product_deleted").toString(), lang.getCurrentLang());
    }

    @Override
    public ResponseDto<ProductDto> restoreById(Long id) {
        Properties p = lang.load("product", "messages.properties");
        ProductDto dto = findById(id).getData();
        if (dto == null) {
            return new ResponseDto<>(false, null, p.get("product_not_found_by_id").toString(), lang.getCurrentLang());
        }
        ProductEntity entity = mappingService.mapDtoToEntity(dto);
        entity.setActive(true);
        dto = mappingService.mapEntityToDto(entity);

        return new ResponseDto<>(true, dto, p.get("product_restored").toString(), lang.getCurrentLang());
    }

    @Override
    public ResponseDto<ProductDto> update(ProductDto productDto) {
        Properties p = lang.load("product", "messages.properties");
        ProductEntity entity = mappingService.mapDtoToEntity(productDto);


        Long id = productDto.getId();
        ProductDto dto = findById(id).getData();
        if (dto == null) {
            return new ResponseDto<>(false, null, p.get("product_not_found_by_id").toString(), lang.getCurrentLang());
        }
        entity.setTitle(productDto.getTitle());
        entity.setPrice(productDto.getPrice());

        ProductDto newProduct = mappingService.mapEntityToDto(entity);
        return new ResponseDto<>(true, newProduct, p.get("product_updated").toString(), lang.getCurrentLang());
    }

    @Override
    public ResponseDto<ProductDto> findById(Long id) {
        Properties p = lang.load("product", "messages.properties");
        ProductEntity product = repository.findById(id).orElse(null);
        if (product == null || !product.isActive()) {
            return new ResponseDto<>(false, null, p.get("product_not_found_by_id").toString(), lang.getCurrentLang());
        } else {
            ProductDto dto = mappingService.mapEntityToDto(product);
            return new ResponseDto<>(true, dto, p.get("product_found").toString(), lang.getCurrentLang());
        }
    }

    @Override
    public ResponseDto<List<ProductDto>> findAllActiveProducts() {
        Properties p = lang.load("product", "messages.properties");
        List<ProductDto> foundProducts = repository.findAll()
                .stream()
                .filter(ProductEntity::isActive)
                .map(mappingService::mapEntityToDto)
                .toList();
        return new ResponseDto<>(true, foundProducts, p.get("all_product_found").toString(), lang.getCurrentLang());
    }

}
