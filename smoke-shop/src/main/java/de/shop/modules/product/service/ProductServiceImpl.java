package de.shop.modules.product.service;

import de.shop.core.components.LanguageResolver;
import de.shop.core.exceptions.*;
import de.shop.modules.product.domain.dto.InputProductDto;
import de.shop.modules.product.domain.dto.OutputProductAdminDto;
import de.shop.modules.product.domain.entity.ProductEntity;
import de.shop.modules.product.repository.interfaces.ProductRepository;
import de.shop.modules.product.service.mapping.ProductMappingService;
import de.shop.modules.users.domain.entity.CartItemEntity;
import de.shop.modules.users.domain.entity.UserEntity;
import de.shop.modules.users.jwt.UserObject;
import de.shop.modules.users.jwt.UserProvider;
import de.shop.modules.users.repository.interfaces.CartItemRepository;
import de.shop.modules.users.repository.interfaces.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMappingService mappingService;
    private final LanguageResolver lang;
private UserProvider userProvider;
private CartItemRepository cartRepository;
private UserRepository userRepository;
    public ProductServiceImpl(ProductRepository repository, ProductMappingService mappingService, LanguageResolver lang,UserProvider userProvider,CartItemRepository cartRepository,UserRepository userRepository) {
        this.repository = repository;
        this.mappingService = mappingService;
        this.lang = lang;
        this.userProvider = userProvider;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OutputProductAdminDto save(InputProductDto inputDto) {
        Properties p = lang.load("product", "messages");
ProductEntity product = mappingService.mapDtoToEntity(inputDto);

        try {
            repository.save(product);
        } catch (Exception e) {
            throw new ProductNotSavedException(((String) p.get("product_not_saved")));
        }
        OutputProductAdminDto prod = mappingService.mapEntityToDto(product);
        return prod;
    }

    @Override
    public void archiveById(Long id) {
        Properties p = lang.load("product", "messages");
        ProductEntity entity = repository.findById(id).orElseThrow( () -> new ProductNotFoundException(((String) p.get("product_not_found"))));
        if (entity.getActive()) {
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
        oldEntity.setActive(entity.getActive());
        oldEntity.setQuantity(entity.getQuantity());

        return oldEntity;
    }

    @Override
    public OutputProductAdminDto findByIdForUser(Long id) {
        Properties p = lang.load("product", "messages");

        ProductEntity entity = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(((String) p.get("product_not_found_by_id"))));
        if (!entity.getActive()) {
            throw new ProductNotFoundException(((String) p.get("product_not_active")));
        } else {
            return mappingService.mapEntityToDto(entity);
        }
    }

    @Override
    public ProductEntity findByIdForAdmin(Long id) {
        Properties p = lang.load("product", "messages");

        ProductEntity entity = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(((String) p.get("product_not_found_by_id"))));
        if (!entity.getActive()) {
            throw new ProductNotFoundException(((String) p.get("product_not_found_by_id")));
        }

        return entity;
    }

    @Override
    public List<OutputProductAdminDto> findAllActiveProductsForUsers() {
        return repository.findAll()
                .stream()
                .filter(ProductEntity::getActive)
                .map(mappingService::mapEntityToDto)
                .toList();
    }

    @Override
    public List<OutputProductAdminDto> findAllActiveProductsForAdmin() {
        return repository.findAll()
                .stream()
                .filter(ProductEntity::getActive)
                .map(productEntity ->
        mappingService.mapEntityToDto(productEntity)
                        )
                .toList();
    }
    public boolean addItemCart(Long id) throws CartItemException, UserSearchException{
        UserObject u = userProvider.getUserObject();
Optional<CartItemEntity> c = cartRepository.findByUserEntityIdAndProductEntityId(u.getId(),id);
if (c.isPresent()) {
    return false;
}
Optional<UserEntity> user = userRepository.findByEmail(u.getEmail());
if (!user.isPresent()) {
    throw new UserSearchException("Not found user");
}
Optional<ProductEntity> product = repository.findById(id);
if (!product.isPresent()) {
    throw new UserSearchException("not product");
}
CartItemEntity cie = new CartItemEntity();
cie.setUser(user.get());
cie.setProduct(product.get());
try {
cartRepository.save(cie);
} catch (DataAccessException e) {
    throw new DBException("ups");
}



    return true;
    }

}
