package de.shop.modules.customer.service;

import de.shop.core.components.LanguageResolver;
import de.shop.core.components.ResponseDto;
import de.shop.core.exceptions.CustomerInactiveException;
import de.shop.core.exceptions.CustomerNotFoundException;
import de.shop.core.exceptions.ProductNotFoundException;
import de.shop.modules.cart.domain.CartEntity;
import de.shop.modules.customer.domain.CustomerDto;
import de.shop.modules.customer.domain.CustomerEntity;
import de.shop.modules.customer.repository.CustomerRepository;
import de.shop.modules.customer.service.mapping.CustomerMappingService;
import de.shop.modules.order.domain.dto.OrderDto;
import de.shop.modules.order.domain.entity.OrderEntity;
import de.shop.modules.order.repository.OrderRepository;
import de.shop.modules.order.service.mapping.OrderMappingService;
import de.shop.modules.orderHistory.domain.OrderHistoryDto;
import de.shop.modules.orderHistory.domain.OrderHistoryEntity;
import de.shop.modules.product.domain.dto.ProductDto;
import de.shop.modules.product.domain.entity.ProductEntity;
import de.shop.modules.product.repository.interfaces.ProductRepository;
import de.shop.modules.product.service.mapping.ProductMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMappingService mappingService;
    private final ProductRepository productRepository;
    private final ProductMappingService productMappingService;

    private final OrderRepository orderRepository;

    private final OrderMappingService orderMappingService;
    private final String currentLanguage;

    @Autowired
    public CustomerServiceImpl(CustomerRepository repository, CustomerMappingService mappingService, ProductRepository productRepository, ProductMappingService productMappingService, OrderRepository orderRepository, OrderMappingService orderMappingService, LanguageResolver lang) {
        this.repository = repository;
        this.mappingService = mappingService;
        this.productRepository = productRepository;
        this.productMappingService = productMappingService;
        this.orderRepository = orderRepository;
        this.orderMappingService = orderMappingService;
        Properties p = lang.load("customers", "messages");
        this.currentLanguage = lang.getCurrentLang();
    }


    @Override
    public ResponseDto<CustomerDto> save(CustomerDto dto) {
        CustomerEntity entity = mappingService.mapDtoToEntity(dto);
        CartEntity cart = new CartEntity();
        entity.setCart(cart);
        cart.setCustomer(entity);
        repository.save(entity);
        CustomerDto responseDto = mappingService.mapEntityToDto(entity);
        return new ResponseDto<>(true, responseDto, "customer_saved", currentLanguage);
    }

    @Override
    public ResponseDto<CustomerDto> getById(Long id) {
        CustomerEntity entity = repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(
                "Customer with id " + id + "cannot be found"));
        CustomerDto dto = mappingService.mapEntityToDto(entity);
        return new ResponseDto<>(true, dto, "customer_found", currentLanguage);
    }

    @Override
    public ResponseDto<List<CustomerDto>> getAllActiveCustomers() {
        List<CustomerDto> customerList = repository.findAll()
                .stream()
                .filter(CustomerEntity::isActive)
                .map(mappingService::mapEntityToDto)
                .toList();
        return new ResponseDto<>(true, customerList, "found_all_active_customers", currentLanguage);
    }

    @Override
    public ResponseDto<CustomerDto> getActiveCustomerById(Long id) {
        CustomerDto dto = getById(id).getData();
        CustomerEntity entity = mappingService.mapDtoToEntity(dto);

        if (!entity.isActive()) {
            throw new CustomerInactiveException("Customer is not active");
        }
        CustomerDto responseDto = mappingService.mapEntityToDto(entity);
        return new ResponseDto<>(true, responseDto, "customer_found", currentLanguage);

    }

    @Override
    public ResponseDto<CustomerDto> update(CustomerDto dto) {
        Long id = dto.getId();
        CustomerEntity customer = repository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("Customer by id " + id + " not found")
        );

        customer.setName(dto.getName());
        CustomerDto responseDto = mappingService.mapEntityToDto(customer);
        return new ResponseDto<>(true, responseDto, "customer_updated", currentLanguage);
    }

    @Override
    public ResponseDto<CustomerDto> deleteById(Long id) {
        CustomerDto dto = getById(id).getData();
        CustomerEntity entity = mappingService.mapDtoToEntity(dto);
        entity.setActive(false);

        CustomerDto responseDto = mappingService.mapEntityToDto(entity);

        return new ResponseDto<>(true, responseDto, "customer_set_not_active", currentLanguage);
    }

//    @Override
//    public ResponseDto<CustomerDto> deleteByName(String name) {
//        CustomerEntity entity = repository.findByName(name).orElseThrow(() -> new CustomerNotFoundException(
//                "Customer with name '" + name + "' cannot be found"));
//        entity.setActive(false);
//
//        CustomerDto responseDto = mappingService.mapEntityToDto(entity);
//
//        return new ResponseDto<>(true, responseDto, "customer_set_not_active", currentLanguage);
//    }

    @Override
    public ResponseDto<CustomerDto> restoreById(Long id) {
        CustomerDto dto = getById(id).getData();
        CustomerEntity entity = mappingService.mapDtoToEntity(dto);
        entity.setActive(true);
        dto = mappingService.mapEntityToDto(entity);

        return new ResponseDto<>(true, dto, "customer_restored", currentLanguage);
    }

    @Override
    public ResponseDto<Integer> getActiveCustomersNumber() {
        int count = getAllActiveCustomers().getData().size();
        return new ResponseDto<>(true, count, "all_active_customers_count", currentLanguage);
    }

    @Override
    public ResponseDto<BigDecimal> getCartTotalCost(Long customerId) {

        CustomerEntity customer = repository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id %d not found", customerId)));
        BigDecimal cartTotalCost = customer.getCart().getCartTotalCost();

        return new ResponseDto<>(true, cartTotalCost, "cart_total_cost", currentLanguage);
    }


    @Override
    public ResponseDto<ProductDto> addProductToCustomersCart(Long customerId, Long productId) {

        CustomerEntity customer = repository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id %d not found", customerId)));
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(String.format("Product with id %d not found", productId)));
        customer.getCart().addProduct(productEntity);
        ProductDto productDto = productMappingService.mapEntityToDto(productEntity);

        return new ResponseDto<>(true, productDto, "add_product_to_cart", currentLanguage);
    }

    @Override
    public ResponseDto<String> removeProductFromCustomersCart(Long customerId, Long productId) {

        CustomerEntity customer = repository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id %d not found", customerId)));
        customer.getCart().removeProductById(productId);

        String confirmation = String.format("Product with id %d is deleted", productId);

        return new ResponseDto<>(true, confirmation, "remove_product_from_cart", currentLanguage);
    }

    @Override
    public ResponseDto<String> clearCart(Long customerId) {

        CustomerEntity customer = repository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id %d not found", customerId)));
        customer.getCart().deleteAllProducts();

        String confirmation = String.format("All products from user with id %d cart are deleted", customerId);


        return new ResponseDto<>(true, confirmation, "clear_cart", currentLanguage);
    }

    @Override
    public ResponseDto<List<ProductDto>> getCart(Long customerId) {

        CustomerEntity customer = repository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id %d not found", customerId)));

        List<ProductDto> productDtoList = customer.getCart().getAllActiveProducts().stream().map(productMappingService::mapEntityToDto).toList();

        return new ResponseDto<>(true, productDtoList, "get_cart", currentLanguage);
    }

    @Override
    public ResponseDto<List<OrderDto>> getOrderHistory(Long customerId) {

        CustomerEntity customer = repository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id %d not found", customerId)));

        List<OrderDto> orderDtoList = customer.getOrderHistory().getOrders().stream().map(orderMappingService::mapEntityToDto).toList();

        return new ResponseDto<>(true, orderDtoList, "get_order_history", currentLanguage);
    }

    @Override
    public ResponseDto<OrderDto> addOrderToHistory(Long customerId, Long orderId) {

        CustomerEntity customer = repository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id %d not found", customerId)));
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new ProductNotFoundException(String.format("Order with id %d not found", orderId)));

        customer.getOrderHistory().addOrderToHistory(orderEntity);

        OrderDto orderDto = orderMappingService.mapEntityToDto(orderEntity);


        return new ResponseDto<>(true, orderDto, "add_order_to_order_history", currentLanguage);
    }

    @Override
    public ResponseDto<String> removeOrderFromHistory(Long customerId, Long orderId) {

        CustomerEntity customer = repository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id %d not found", customerId)));

        customer.getOrderHistory().removeOrderById(orderId);

        String confirmation = String.format("Order with id %d was removed", orderId);

        return new ResponseDto<>(true, confirmation, "order_with_id_removed", currentLanguage);
    }

    @Override
    public ResponseDto<String> clearOrderHistory(Long customerId) {

        CustomerEntity customer = repository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id %d not found", customerId)));

        customer.getOrderHistory().clearOrderHistory();

        String confirmation = String.format("OrderHistory for customer with id %d was cleared", customerId);

        return new ResponseDto<>(true, confirmation, "clear_order_history", currentLanguage);
    }

}

