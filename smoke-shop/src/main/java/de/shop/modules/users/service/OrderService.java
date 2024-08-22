package de.shop.modules.users.service;

import de.shop.core.exceptions.OrderNotFoundException;
import de.shop.modules.users.domain.dto.OutputOrderDataDto;
import de.shop.modules.users.domain.dto.OutputOrderDto;
import de.shop.modules.users.domain.dto.OutputOrderItemDto;
import de.shop.modules.users.domain.dto.OutputOrderNameDto;
import de.shop.modules.users.domain.entity.OrderEntity;
import de.shop.modules.users.domain.entity.OrderItemEntity;
import de.shop.modules.users.jwt.UserObject;
import de.shop.modules.users.jwt.UserProvider;
import de.shop.modules.users.repository.interfaces.OrderItemRepository;
import de.shop.modules.users.repository.interfaces.OrderRepository;
import de.shop.modules.users.service.mapping.OrderMappingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private OrderMappingService orderMappingService;
    private OrderItemRepository orderItemRepository;
    private UserProvider userProvider;

    public OrderService(OrderRepository orderRepository, OrderMappingService orderMappingService, OrderItemRepository orderItemRepository, UserProvider userProvider) {
        this.orderRepository = orderRepository;
        this.orderMappingService = orderMappingService;
        this.orderItemRepository = orderItemRepository;
        this.userProvider = userProvider;
    }

    public List<OutputOrderNameDto> listOrders() {
        UserObject u = userProvider.getUserObject();
        List<OutputOrderNameDto> l = orderRepository.findByUserEntityIdOrderByDateDesc(u.getId()).stream()
                .map(orderEntity -> {
                    OutputOrderNameDto out = new OutputOrderNameDto();
                    out.setId(orderEntity.getId());
                    out.setDate(orderEntity.getDate());
                    out.setTotal(orderEntity.getTotal());
                    return out;
                }).toList();
        return l;
    }

    public OutputOrderDto info(Long id) throws OrderNotFoundException {
        UserObject u = userProvider.getUserObject();

        Optional<OrderEntity> o = orderRepository.findByUserEntityIdAndId(u.getId(), id);
        if (!o.isPresent()) {
            throw new OrderNotFoundException("not order");
        }
        OrderEntity order = o.get();
        List<OrderItemEntity> orderItemEntity = orderItemRepository.findByOrderEntityIdOrderByIdDesc(order.getId());
        List<OutputOrderItemDto> l = new ArrayList<>();
        for (OrderItemEntity item : orderItemEntity) {
            OutputOrderItemDto out = new OutputOrderItemDto();
            out.setId(item.getId());
            out.setTitle(item.getTitle());
            out.setPrice(item.getPrice());
            out.setTotal(item.getTotal());
            out.setQuantity(item.getQuantity());
            out.setProductId(item.getProduct().getId());
            out.setImgUrl(item.getProduct().getImgUrl());

            l.add(out);
        }
        OutputOrderDataDto data = new OutputOrderDataDto();
        data.setId(order.getId());
        data.setTotal(order.getTotal());
        data.setOrderStatus(order.getOrderStatus());
        data.setDeliveryAddress(order.getDeliveryAddress());
        data.setBillingAddress(order.getBillingAddress());
        data.setEmail(order.getEmail());
        data.setPhone(order.getPhone());
        data.setDate(order.getDate());
        data.setPayments(order.getPayments());

        OutputOrderDto output = new OutputOrderDto();
        output.setData(data);
        output.setProducts(l);

        return output;
    }
}
