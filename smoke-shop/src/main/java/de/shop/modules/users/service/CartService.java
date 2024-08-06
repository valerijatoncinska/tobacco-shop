package de.shop.modules.users.service;

import de.shop.core.exceptions.*;
import de.shop.modules.product.domain.entity.ProductEntity;
import de.shop.modules.product.repository.interfaces.ProductRepository;
import de.shop.modules.product.service.ProductServiceImpl;
import de.shop.modules.users.domain.dto.CartDto;
import de.shop.modules.users.domain.dto.InputCartQuantityDto;
import de.shop.modules.users.domain.entity.CartItemEntity;
import de.shop.modules.users.jwt.UserObject;
import de.shop.modules.users.jwt.UserProvider;
import de.shop.modules.users.repository.interfaces.CartItemRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private CartItemRepository cartRepository;
    private UserProvider userProvider;
    private ProductRepository productRepository;

    public CartService(CartItemRepository cartRepository, UserProvider userProvider, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userProvider = userProvider;
        this.productRepository = productRepository;
    }

    public CartDto cartQuantity(Long id, InputCartQuantityDto dto) throws CartItemException, CartConflictException {
        Optional<CartItemEntity> optional = cartRepository.findById(id);
        if (!optional.isPresent()) {
            throw new CartItemException("not found cart item");
        }
        // вытягиваем item корзины.
        CartItemEntity cartItemEntity = optional.get();
        // вытягиваем сам продукт.
        ProductEntity product = cartItemEntity.getProduct();
        int stock, quantity;
        if (dto.getType().equals("plus")) {
            stock = product.getQuantity() - 1; // отнимаем от продукта на складе.
            quantity = cartItemEntity.getQuantity() + 1; // добавили товар в корзину
        }
        else {
            stock = product.getQuantity() + 1; // отнимаем от продукта на складе.
            quantity = cartItemEntity.getQuantity() - 1; // добавили товар в корзину
        }
if (stock<0) {
    throw new CartConflictException("not product");
}
// остановим код, если мы пытаемся сделать ноль товара
if (quantity<1) {
    throw new CartConflictException("error");
}
product.setQuantity(stock); // Записали количество на складе.
        cartItemEntity.setQuantity(quantity); // Записали количество в элемент корзины.

// далее вписываем данные.
        try {
            productRepository.save(product);
            cartRepository.save(cartItemEntity);
            CartDto cartDto = new CartDto();
            cartDto.setId(cartItemEntity.getId());
            cartDto.setTitle(product.getTitle());
            cartDto.setStock(product.getQuantity());
            cartDto.setQuantity(cartItemEntity.getQuantity());
cartDto.setProductId(product.getId());
cartDto.setPrice(product.getPrice());
            return cartDto;
        } catch (DataAccessException e) {
            throw new DBException("error");
        }

    }

    public boolean drop(Long id) throws DBException {
        Optional<CartItemEntity> optional = cartRepository.findById(id);
        if (!optional.isPresent()) {
            return false;
        }
// далее нужно вернуть продукты на склад.
        CartItemEntity cartItemEntity = optional.get();
        ProductEntity product = cartItemEntity.getProduct();
        int stock = product.getQuantity() + cartItemEntity.getQuantity();
        product.setQuantity(stock);
        try {
            productRepository.save(product);
        } catch (DataAccessException e) {
            throw new DBException("error");
        }

        cartRepository.deleteById(id);
        return true;
    }

    public List<CartDto> list() {
        UserObject u = userProvider.getUserObject();
        return cartRepository.findByUserEntityId(u.getId()).stream()
                .map(cartItemEntity -> {
                    CartDto cart = new CartDto();
                    cart.setId(cartItemEntity.getId());
                    cart.setQuantity(cartItemEntity.getQuantity());
                    cart.setStock(cartItemEntity.getStock());
                    ProductEntity p = cartItemEntity.getProduct();
                    cart.setTitle(p.getTitle());
                    cart.setProductId(p.getId());
cart.setPrice(p.getPrice());
                    return cart;
                })
                .toList();
    }


}
