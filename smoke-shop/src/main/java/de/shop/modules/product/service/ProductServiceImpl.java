package de.shop.modules.product.service;

import de.shop.core.components.LanguageResolver;
import de.shop.core.components.ResponseDto;
import de.shop.core.exceptions.CustomerNotFoundException;
import de.shop.core.exceptions.ProductNotFoundException;
import de.shop.modules.product.domain.dto.ProductDto;
import de.shop.modules.product.domain.entity.ProductEntity;
import de.shop.modules.product.repository.interfaces.ProductRepository;
import de.shop.modules.product.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Properties;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMappingService mappingService;
    private Properties p;
    private final LanguageResolver lang;
    private String currentLanguage;

    public ProductServiceImpl(ProductRepository repository, ProductMappingService mappingService, LanguageResolver lang) {
        this.repository = repository;
        this.mappingService = mappingService;
        this.lang = lang;
        this.p = lang.load("product", "messages");
        this.currentLanguage = lang.getCurrentLang();
    }

    @Override
    public ResponseDto<ProductDto> save(ProductDto product) {
        ProductEntity entity = mappingService.mapDtoToEntity(product);

        try {
            repository.save(entity);
        } catch (Exception e) {
            return new ResponseDto<>(false, product, "product_not_saved", currentLanguage);
        }
        mappingService.mapEntityToDto(entity);
        return new ResponseDto<>(true, product, "product_saved", currentLanguage);
    }

    @Override
    public ResponseDto<ProductDto> deleteById(Long id) {
        ProductDto dto = findById(id).getData();
        ProductEntity entity = mappingService.mapDtoToEntity(dto);
        entity.setActive(false);
        dto = mappingService.mapEntityToDto(entity);

        return new ResponseDto<>(true, dto, "product_deleted", currentLanguage);
    }

    @Override
    public ResponseDto<ProductDto> restoreById(Long id) {
        ProductDto dto = findById(id).getData();

        ProductEntity entity = mappingService.mapDtoToEntity(dto);
        entity.setActive(true);
        dto = mappingService.mapEntityToDto(entity);

        return new ResponseDto<>(true, dto, p.get("product_restored").toString(), currentLanguage);
    }

    @Override
    public ResponseDto<ProductDto> update(ProductDto productDto) {

        Long id = productDto.getId();
        ProductDto dto = findById(id).getData();
        ProductEntity entity = mappingService.mapDtoToEntity(productDto);

        entity.setTitle(dto.getTitle());
        entity.setPrice(dto.getPrice());

        ProductDto newProduct = mappingService.mapEntityToDto(entity);
        return new ResponseDto<>(true, newProduct, "product_updated", currentLanguage);
    }

    @Override
    public ResponseDto<ProductDto> findById(Long id) {

        ProductEntity entity = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(
                "Product with id " + id + "cannot be found"));
        if (!entity.isActive()) {
            return new ResponseDto<>(false, mappingService.mapEntityToDto(entity),
                    "product_is_inactive", currentLanguage);
        }
        ProductDto dto = mappingService.mapEntityToDto(entity);
        return new ResponseDto<>(true, dto, "product_found", currentLanguage);
    }

    @Override
    public ResponseDto<List<ProductDto>> findAllActiveProducts() {
        List<ProductDto> foundProducts = repository.findAll()
                .stream()
                .filter(ProductEntity::isActive)
                .map(mappingService::mapEntityToDto)
                .toList();
        return new ResponseDto<>(true, foundProducts, "all_product_found", currentLanguage);
    }

}
