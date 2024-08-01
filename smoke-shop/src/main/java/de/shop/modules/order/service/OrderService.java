package de.shop.modules.order.service;

import de.shop.core.components.ResponseDto;
import de.shop.modules.order.domain.dto.OrderDto;

import java.util.List;

public interface OrderService {

    ResponseDto<OrderDto> save(OrderDto dto);

    ResponseDto<OrderDto> deleteById(Long id);

    ResponseDto<OrderDto> update(OrderDto dto);

    ResponseDto<?> findById(Long id);

    ResponseDto<List<OrderDto>> findAllOrders();

}


