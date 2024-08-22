package de.shop.modules.users.service;

import de.shop.core.exceptions.AddressNotfoundException;
import de.shop.core.exceptions.CartConflictException;
import de.shop.core.exceptions.CartItemException;
import de.shop.core.exceptions.DBException;
import de.shop.modules.product.domain.entity.ProductEntity;
import de.shop.modules.product.repository.interfaces.ProductRepository;
import de.shop.modules.users.domain.dto.*;
import de.shop.modules.users.domain.entity.AddressEntity;
import de.shop.modules.users.domain.entity.CartItemEntity;
import de.shop.modules.users.domain.entity.OrderEntity;
import de.shop.modules.users.domain.entity.OrderItemEntity;
import de.shop.modules.users.jwt.UserObject;
import de.shop.modules.users.jwt.UserProvider;
import de.shop.modules.users.repository.interfaces.AddressRepository;
import de.shop.modules.users.repository.interfaces.CartItemRepository;
import de.shop.modules.users.repository.interfaces.OrderItemRepository;
import de.shop.modules.users.repository.interfaces.OrderRepository;
import de.shop.modules.users.service.mapping.OrderMappingService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CartService {
    private CartItemRepository cartRepository;
    private UserProvider userProvider;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private OrderMappingService orderMappingService;
    private AddressRepository addressRepository;
    private OrderItemRepository orderItemRepository;

    public CartService(CartItemRepository cartRepository, UserProvider userProvider, ProductRepository productRepository, OrderRepository orderRepository, OrderMappingService orderMappingService, AddressRepository addressRepository, OrderItemRepository orderItemRepository) {
        this.cartRepository = cartRepository;
        this.userProvider = userProvider;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderMappingService = orderMappingService;
        this.addressRepository = addressRepository;
        this.orderItemRepository = orderItemRepository;
    }

    private HashMap<String, String> address(Long user, Long id) throws AddressNotfoundException {
        Optional<AddressEntity> da = addressRepository.findByUserEntityIdAndId(user, id);
        if (!da.isPresent()) {
            throw new AddressNotfoundException("not address");
        }
        AddressEntity address = da.get();
        String location = address.getName() + ". " + address.getStreet() + " " + address.getHouse() + ". " + address.getPostalCode() + " " + address.getLocality() + ". " + address.getRegion() + " " + address.getCountry();
        String email = address.getEmail();
        String phone = address.getPhone();
        HashMap<String, String> l = new HashMap<>();
        l.put("address", location);
        l.put("email", email);
        l.put("phone", phone);
        return l;
    }

    public OutputOrderDataDto order(InputOrderDto dto) throws DBException, AddressNotfoundException {
        if (!dto.getPayments().equals("paypal")) {
            throw new AddressNotfoundException("not payments");
        }
        UserObject u = userProvider.getUserObject();
        HashMap<String, String> deliveryAddress = address(u.getId(), dto.getDeliveryAddress());
        HashMap<String, String> billingAddress = address(u.getId(), dto.getBillingAddress());
        // Заполняем OrderEntity
        OrderEntity entity = new OrderEntity();
        entity.setOrderStatus("pending");
        entity.setUserEntity(userProvider.getUserEntity());
        entity.setDate(LocalDateTime.now());
        entity.setDeliveryAddress(deliveryAddress.get("address"));
        entity.setBillingAddress(billingAddress.get("address"));
// Надо пройтись по CartItemEntity - элементы корзины.
        List<CartItemEntity> carts = cartRepository.findByUserEntityId(u.getId());
        List<OrderItemEntity> orderItemEntity = new ArrayList<>();
        BigDecimal money = BigDecimal.ZERO;
        for (CartItemEntity cart : carts) {
            BigDecimal quantity = BigDecimal.valueOf(cart.getQuantity());
            BigDecimal price = cart.getProduct().getPrice();
            BigDecimal total = price.multiply(quantity);
            money = money.add(total);
            OrderItemEntity oie = new OrderItemEntity();
            oie.setTitle(cart.getProduct().getTitle());
            oie.setPrice(cart.getProduct().getPrice());
            oie.setQuantity(cart.getQuantity());
            oie.setTotal(total);
            oie.setProduct(cart.getProduct());
            oie.setOrderEntity(entity);
            orderItemEntity.add(oie);
            cartRepository.delete(cart);
        }

        entity.setTotal(money);
        entity.setPayments(dto.getPayments());
        entity.setEmail(billingAddress.get("email"));
        entity.setPhone(billingAddress.get("phone"));

        try {
            orderRepository.save(entity);
            orderItemRepository.saveAll(orderItemEntity);
        } catch (DataAccessException e) {
            throw new DBException("Error saving order: ");
        }
        OutputOrderDataDto out = orderMappingService.mapEntityToDto(entity);
        return out;
    }

    @Transactional
    public boolean clear() {
        UserObject u = userProvider.getUserObject();
        List<CartItemEntity> c = cartRepository.findByUserEntityId(u.getId());
        for (CartItemEntity cart : c) {
            if (!drop(cart.getId())) {
                return false;
            }
        }
        return true;
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
            quantity = cartItemEntity.getQuantity() + 1; // добавили товар в корзин
            // у
        } else {
            stock = product.getQuantity() + 1; // отнимаем от продукта на складе.
            quantity = cartItemEntity.getQuantity() - 1; // добавили товар в корзину
        }
        if (stock < 0) {
            throw new CartConflictException("not product");
        }
// остановим код, если мы пытаемся сделать ноль товара
        if (quantity < 1) {
            throw new CartConflictException("error");
        }
        product.setQuantity(stock); // Записали количество на складе.
        cartItemEntity.setQuantity(quantity); // Записали количество в элемент корзины.
        BigDecimal q = BigDecimal.valueOf(quantity);
        BigDecimal price = product.getPrice().multiply(q);
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
            cartDto.setTotalPrice(price);
            cartDto.setPrice(product.getPrice());
            cartDto.setImgUrl(product.getImgUrl());
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

    public OutputCartDto list() {
        UserObject u = userProvider.getUserObject();
        OutputCartDto outputCartDto = new OutputCartDto();
        AtomicReference<BigDecimal> money = new AtomicReference<>(BigDecimal.ZERO);
        List<CartDto> l = cartRepository.findByUserEntityId(u.getId()).stream()
                .map(cartItemEntity -> {
                    CartDto cart = new CartDto();
                    cart.setId(cartItemEntity.getId());
                    cart.setQuantity(cartItemEntity.getQuantity());
                    cart.setStock(cartItemEntity.getStock());
                    ProductEntity p = cartItemEntity.getProduct();
                    cart.setTitle(p.getTitle());
                    cart.setProductId(p.getId());
                    cart.setPrice(p.getPrice());
                    BigDecimal q = BigDecimal.valueOf(cartItemEntity.getQuantity());
                    BigDecimal total = p.getPrice().multiply(q);
                    money.updateAndGet(m -> m.add(total));
                    cart.setTotalPrice(total);
                    cart.setImgUrl(p.getImgUrl());
                    return cart;
                })
                .toList();
        outputCartDto.addData("total", money);
        outputCartDto.addProducts(l);
        return outputCartDto;
    }

    public Long findByProductId(Long id) {
        UserObject u = userProvider.getUserObject();
        CartItemEntity cartItemEntity = cartRepository.findByUserEntityIdAndProductEntityId(u.getId(), id).orElseThrow(() -> new CartItemException("not found cart item"));

        return cartItemEntity.getId();

    }

}
