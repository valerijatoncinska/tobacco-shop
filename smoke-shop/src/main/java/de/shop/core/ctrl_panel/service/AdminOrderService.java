package de.shop.core.ctrl_panel.service;

import de.shop.core.ctrl_panel.domain.dto.OutputOrderNameAdminDto;
import de.shop.core.ctrl_panel.repository.AdminOrderRepository;
import de.shop.core.exceptions.DBException;
import de.shop.core.exceptions.OrderNotFoundException;
import de.shop.modules.users.domain.dto.OutputOrderDataDto;
import de.shop.modules.users.domain.dto.OutputOrderDto;
import de.shop.modules.users.domain.dto.OutputOrderItemDto;
import de.shop.modules.users.domain.entity.OrderEntity;
import de.shop.modules.users.domain.entity.OrderItemEntity;
import de.shop.modules.users.jwt.UserObject;
import de.shop.modules.users.jwt.UserProvider;
import de.shop.modules.users.repository.interfaces.OrderItemRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс сервис, по управлению заказами со стороны админа.
 */
@Service
public class AdminOrderService {
    private AdminOrderRepository repository; // репозиторий для заказов со стороны админа
    private UserProvider provider; // провайдер для пользователей
    private OrderItemRepository orderItemRepository; // репозиторий для элементов внутри заказов.

    public AdminOrderService(AdminOrderRepository repository, UserProvider provider, OrderItemRepository orderItemRepository) {
        this.repository = repository;
        this.provider = provider;
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * Такой себе передатчик данных для информации о заказе
     *
     * @param id id для заказа
     * @return вернет OutputOrderDto
     */
    public OutputOrderDto info(Long id) {
        return dataOrder(id);
    }

    /**
     * Метод формирует данные о конкретном заказе
     *
     * @param id id заказа
     * @return вернет OutputOrderDto
     * @throws OrderNotFoundException перехвадчик ошибок
     */
    private OutputOrderDto dataOrder(Long id) throws OrderNotFoundException {
        UserObject u = provider.getUserObject();

        Optional<OrderEntity> o = repository.findById(id);
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

    /**
     * Метод исправляет статус
     *
     * @param id     id заказа
     * @param status статус
     * @return возвращает OutputOrderDto
     * @throws OrderNotFoundException перехват ошибок
     * @throws DBException            перехват ошибок
     */
    public OutputOrderDto status(Long id, String status) throws OrderNotFoundException, DBException {
        Optional<OrderEntity> opt = repository.findById(id);
        if (!opt.isPresent()) {
            throw new OrderNotFoundException("error");
        }
        OrderEntity entity = opt.get();
        entity.setOrderStatus(status);
        try {
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new DBException("ERROR");
        }
        return dataOrder(entity.getId());
    }

    /**
     * Метод выводит список заказов, сортируя по статусам.
     *
     * @param status Статус
     * @return возвращает List<OutputOrderNameAdminDto>
     */
    public List<OutputOrderNameAdminDto> list(String status) {
        return repository.findByOrderStatusOrderByIdDesc(status).stream()
                .map(entity -> {
                    OutputOrderNameAdminDto dto = new OutputOrderNameAdminDto();
                    dto.setId(entity.getId());
                    dto.setDate(entity.getDate());
                    dto.setTotal(entity.getTotal());
                    dto.setUserId(entity.getUserEntity().getId());
                    return dto;
                }).toList();
    }

}
