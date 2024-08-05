//package de.shop.modules.cart.service;
//
//import de.shop.modules.cart.domain.CartEntity;
//import de.shop.modules.cart.repository.CartRepository;
//import de.shop.modules.product.domain.entity.ProductEntity;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//public class CartServiceImpl implements CartService {
//
//    CartRepository repository;
//
//    public CartServiceImpl(CartRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public void addProduct(ProductEntity product, Long cartId) {
//        if (product.isActive()) {
//            CartEntity cart = repository.findById(cartId).orElseThrow(() -> new RuntimeException(String.format("Cart with id %d not found", cartId)));
//            cart.getProducts().add(product);
//        }
//    }
//
//    @Override
//    public BigDecimal getCartTotalCost(Long cartId) {
//        CartEntity cart = repository.findById(cartId).orElseThrow(() -> new RuntimeException(String.format("Cart with id %d not found", cartId)));
//        return cart.getProducts().stream().filter(ProductEntity::isActive).map(ProductEntity::getPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
//    }
//
//    @Override
//    public List<ProductEntity> getAllActiveProducts(Long cartId) {
//        CartEntity cart = repository.findById(cartId).orElseThrow(() -> new RuntimeException(String.format("Cart with id %d not found", cartId)));
//
//        return cart.getProducts().stream().filter(ProductEntity::isActive).toList();
//    }
//
//    @Override
//    public void deleteAllProducts(Long cartId) {
//        CartEntity cart = repository.findById(cartId).orElseThrow(() -> new RuntimeException(String.format("Cart with id %d not found", cartId)));
//        cart.getProducts().clear();
//    }
//}
