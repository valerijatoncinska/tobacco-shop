//package de.shop.modules.order.service;
//
//import de.shop.core.components.LanguageResolver;
//import de.shop.core.components.ResponseDto;
//import de.shop.core.exceptions.OrderNotDeleted;
//import de.shop.core.exceptions.OrderNotFoundException;
//import de.shop.modules.order.domain.dto.OrderDto;
//import de.shop.modules.order.domain.entity.OrderEntity;
//import de.shop.modules.order.repository.OrderRepository;
//import de.shop.modules.order.service.mapping.OrderMappingService;
//import de.shop.modules.product.domain.entity.ProductEntity;
//import org.springframework.stereotype.Service;
//
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Properties;
//
//@Service
//public class OrderServiceImpl implements OrderService{
//
//    private final OrderRepository repository;
//    private final OrderMappingService mappingService;
//    private Properties p;
//    private String currentLanguage;
//
//    public OrderServiceImpl(OrderRepository repository, OrderMappingService mappingService, LanguageResolver lang) {
//        this.repository = repository;
//        this.mappingService = mappingService;
//        this.p = lang.load("orders", "messages");
//        this.currentLanguage = lang.getCurrentLang();
//    }
//
//    @Override
//    public ResponseDto<OrderDto> save(OrderDto dto) {
//        OrderEntity entity = mappingService.mapDtoToEntity(dto);
//
//        try {
//            entity.setTotalPrice(dto.getProducts().stream().map(ProductEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
//            entity.setTotalQuantity(dto.getProducts().size());
//            repository.save(entity);
//        } catch (Exception e) {
//            return new ResponseDto<>(false, dto, "order_not_saved", currentLanguage);
//        }
//        mappingService.mapEntityToDto(entity);
//        return new ResponseDto<>(true, dto, "order_saved", currentLanguage);
//    }
//
//    @Override
//    public ResponseDto<OrderDto> deleteById(Long id) {
//        OrderDto dto = (OrderDto) findById(id).getData();
//        OrderEntity entity = mappingService.mapDtoToEntity(dto);
//        try {
//            repository.delete(entity);
//        } catch (Exception e) {
//            throw new OrderNotDeleted("order_not_deleted");
//        }
//        return new ResponseDto<>(true, dto, "order_deleted", currentLanguage);
//    }
//
//    @Override
//    public ResponseDto<OrderDto> update(OrderDto dto) {
//        Long id = dto.getId();
//        OrderDto orderDto = (OrderDto) findById(id).getData();
//        OrderEntity entity = mappingService.mapDtoToEntity(orderDto);
//
//        BigDecimal dtoProductsTotalPrice = dto.getProducts().stream().map(ProductEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
//        int dtoProductsTotalQuantity = dto.getProducts().size();
//
//        entity.setStatus(dto.getStatus());
//        entity.setOrderDate(dto.getOrderDate());
//        entity.setProducts(dto.getProducts());
//        entity.setTotalPrice(dtoProductsTotalPrice);
//        entity.setTotalQuantity(dtoProductsTotalQuantity);
//
//        OrderDto newProduct = mappingService.mapEntityToDto(entity);
//        return new ResponseDto<>(true, newProduct, "order_updated", currentLanguage);
//    }
//
//    @Override
//    public ResponseDto<?> findById(Long id) {
//
//        if (!(id == null) && !(String.valueOf(id).isEmpty())) {
//            OrderEntity entity = repository.findById(id).orElseThrow(() -> new OrderNotFoundException("order_not_found_by_id"));
//
//            OrderDto oneDto = mappingService.mapEntityToDto(entity);
//
//            return new ResponseDto<>(true, oneDto, "order_found", currentLanguage);
//
//        } else {
//            List<OrderDto> foundOrders = repository.findAll()
//                    .stream()
//                    .map(mappingService::mapEntityToDto)
//                    .toList();
//            return new ResponseDto<>(true, foundOrders, "all_orders_found", currentLanguage);
//        }
//    }
//
//    @Override
//    public ResponseDto<List<OrderDto>> findAllOrders() {
//        List<OrderDto> foundOrders = repository.findAll()
//                .stream()
//                .map(mappingService::mapEntityToDto)
//                .toList();
//        return new ResponseDto<>(true, foundOrders, "all_orders_found", currentLanguage);
//    }
//}
