package de.shop.modules.product.service;

import de.shop.core.components.LanguageResolver;
import de.shop.core.exceptions.*;
import de.shop.modules.product.domain.dto.InputProductDto;
import de.shop.modules.product.domain.dto.OutputDto;
import de.shop.modules.product.domain.dto.OutputProductAdminDto;
import de.shop.modules.product.domain.dto.OutputProductUserDto;
import de.shop.modules.product.domain.entity.ProductEntity;
import de.shop.modules.product.repository.interfaces.ProductRepository;
import de.shop.modules.product.service.mapping.ProductMappingService;
import de.shop.modules.users.domain.entity.CartItemEntity;
import de.shop.modules.users.domain.entity.UserEntity;
import de.shop.modules.users.jwt.UserObject;
import de.shop.modules.users.jwt.UserProvider;
import de.shop.modules.users.repository.interfaces.CartItemRepository;
import de.shop.modules.users.repository.interfaces.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMappingService mappingService;
    private final LanguageResolver lang;
    private UserProvider userProvider;
    private CartItemRepository cartRepository;
    private UserRepository userRepository;

    public ProductService(ProductRepository repository, ProductMappingService mappingService, LanguageResolver lang, UserProvider userProvider, CartItemRepository cartRepository, UserRepository userRepository) {
        this.repository = repository;
        this.mappingService = mappingService;
        this.lang = lang;
        this.userProvider = userProvider;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean dropProduct(Long id) throws ProductNotFoundException {
        Optional<ProductEntity> p = repository.findById(id);
        if (!p.isPresent()) {
            throw new ProductNotFoundException("not found");
        }
        try {
            cartRepository.deleteByProductEntity_Id(id);
            repository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("NOT PRODUCT DELETED: " + e.getMessage());
            return false;
        } catch (DataIntegrityViolationException e) {
            System.out.println("Нарушается целостность. удаление продукта отклонено " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Проблема с JPA " + e.getMessage());
            return false;
        }
    }

    public OutputDto productInfo(Long id) throws ProductNotFoundException {
        UserObject u = userProvider.getUserObject();
        Optional<ProductEntity> p = repository.findById(id);
        if (!p.isPresent()) {
            throw new ProductNotFoundException(" not found");
        }
        ProductEntity product = p.get();
        OutputDto outputDto = new OutputDto();
//        if (userProvider.role("ROLE_ADMIN")) {
        OutputProductAdminDto outputProductAdminDto = mappingService.mapEntityToAdminDto(product);
        outputDto.setData(outputProductAdminDto);
//        } else {
//            OutputProductUserDto outputProductUserDto = mappingService.mapEntityToUserDto(product);
//            outputDto.setData(outputProductUserDto);
//        }
        return outputDto;
    }

    public OutputProductAdminDto save(InputProductDto inputDto) {
        Properties p = lang.load("products", "messages");
        ProductEntity product = mappingService.mapDtoToEntity(inputDto);

        try {
            repository.save(product);
        } catch (Exception e) {
            throw new ProductNotSavedException(((String) p.get("product.not.saved")));
        }
        OutputProductAdminDto prod = mappingService.mapEntityToAdminDto(product);
        return prod;
    }

    public OutputProductAdminDto updateProduct(Long id, InputProductDto inputDto) throws ProductNotFoundException {
        Properties p = lang.load("products", "messages");
        Optional<ProductEntity> pe = repository.findById(id);
        if (!pe.isPresent()) {
            throw new ProductNotFoundException("Not found");
        }
        ProductEntity productDb = pe.get();
        ProductEntity product = mappingService.mapDtoToEntity(inputDto);
        product.setId(productDb.getId());
        try {
            repository.save(product);
        } catch (Exception e) {
            throw new ProductNotSavedException(((String) p.get("product.not.saved")));
        }
        OutputProductAdminDto prod = mappingService.mapEntityToAdminDto(product);
        return prod;
    }


    public void archiveById(Long id) {
        Properties p = lang.load("product", "messages");
        ProductEntity entity = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(((String) p.get("product_not_found"))));
        if (entity.getActive()) {
            entity.setActive(false);
        } else {
            throw new ProductNotFoundException(((String) p.get("product_already_archived")));
        }
    }

    public ProductEntity restoreById(Long id) {
        Properties p = lang.load("product", "messages");
        ProductEntity entity = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(((String) p.get("product_not_found_by_id"))));

        entity.setActive(true);

        return entity;
    }

    public ProductEntity update(ProductEntity entity) {
        Properties p = lang.load("product", "messages");

        Long id = entity.getId();
        ProductEntity oldEntity = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(((String) p.get("product_not_found_by_id"))));

        oldEntity.setTitle(entity.getTitle());
        oldEntity.setPrice(entity.getPrice());
        oldEntity.setActive(entity.getActive());
        oldEntity.setQuantity(entity.getQuantity());
        oldEntity.setDescription(entity.getDescription());
        oldEntity.setCharacteristics(entity.getCharacteristics());

        return oldEntity;
    }

    private List<OutputProductUserDto> productsForUser() {
        return repository.findAll()
                .stream()
                .filter(ProductEntity::getActive)
                .map(mappingService::mapEntityToUserDto
                )
                .toList();
    }

    private List<OutputProductAdminDto> productsForAdmin() {
        return repository.findAll()
                .stream()
//                .filter(ProductEntity::getActive)
                .map(mappingService::mapEntityToAdminDto
                )
                .toList();
    }


    public OutputDto productsAll() {
        OutputDto o = new OutputDto();
        UserObject u = userProvider.getUserObject();
//        if (userProvider.role("ROLE_ADMIN")) {
        o.setData(productsForAdmin());
//        } else {
//            o.setData(productsForUser());
//        }
        return o;
    }

    public boolean addItemCart(Long id) throws CartItemException, UserSearchException {
        UserObject u = userProvider.getUserObject();
        Optional<CartItemEntity> c = cartRepository.findByUserEntityIdAndProductEntityId(u.getId(), id);
        if (c.isPresent()) {
            return false;
        }
        Optional<UserEntity> user = userRepository.findByEmail(u.getEmail());
        if (!user.isPresent()) {
            throw new UserSearchException("Not found user");
        }
        Optional<ProductEntity> optionalProduct = repository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new UserSearchException("not product");
        }
        ProductEntity product = optionalProduct.get();
        CartItemEntity cie = new CartItemEntity();
        cie.setUser(user.get());
        cie.setProduct(product);
// Производим расчеты.
        int productQuantity = product.getQuantity() - 1;
        if (productQuantity < 0) {
            return false;
        }
        cie.setQuantity(1); // указали количество, которое должно быть в корзине.
        product.setQuantity(productQuantity); // указали, сколько товара осталось на складе.

        try {
            cartRepository.save(cie);
            repository.save(product);
        } catch (DataAccessException e) {
            throw new DBException("ups");
        }


        return true;
    }

}
