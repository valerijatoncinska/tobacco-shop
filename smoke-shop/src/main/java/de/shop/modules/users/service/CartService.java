package de.shop.modules.users.service;

import de.shop.core.exceptions.UserSearchException;
import de.shop.modules.product.domain.entity.ProductEntity;
import de.shop.modules.users.domain.dto.CartDto;
import de.shop.modules.users.domain.entity.CartItemEntity;
import de.shop.modules.users.jwt.UserObject;
import de.shop.modules.users.jwt.UserProvider;
import de.shop.modules.users.repository.interfaces.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private CartItemRepository cartRepository;
    private UserProvider userProvider;

public CartService(CartItemRepository cartRepository,UserProvider userProvider) {
this.cartRepository = cartRepository;
this.userProvider = userProvider;
}
public void drop(Long id) throws UserSearchException  {
cartRepository.deleteById(id);
}
public List<CartDto> list() {
    UserObject u = userProvider.getUserObject();
    return cartRepository.findByUserEntityId(u.getId()).stream()
            .map(cartItemEntity -> {
                CartDto cart = new CartDto();
                cart.setCartElement(cartItemEntity.getId());
                cart.setQuantity(cartItemEntity.getQuantity());
                ProductEntity p = cartItemEntity.getProduct();
                cart.setTitle(p.getTitle());

return cart;
            })
            .toList();
}


}
