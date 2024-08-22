package de.shop.core.ctrl_panel.controller;

import de.shop.core.ctrl_panel.domain.dto.InputStatusDto;
import de.shop.core.ctrl_panel.service.AdminOrderService;
import de.shop.core.exceptions.OrderStatusNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Класс контроллер для админ панели, который выводит данные для заказов от всех участников
 */
@RestController
@RequestMapping("/ctrl-panel/orders")
@Secured("ROLE_ADMIN")
public class AdminOrderController {
    private AdminOrderService service;

    public AdminOrderController(AdminOrderService service) {
        this.service = service;
    }

    /**
     * полная информация про заказ.
     *
     * @param id принимает id заказа
     * @return возвращает OutputOrderDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> info(@PathVariable Long id) {
        return ResponseEntity.ok(service.info(id));
    }

    /**
     * Изменяет статус заказа
     *
     * @param id  id заказа
     * @param dto входящий dto
     * @return возвращает OutputOrderDto
     * @throws OrderStatusNotFoundException перехват ошибок
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<?> status(@PathVariable Long id, @RequestBody InputStatusDto dto) throws OrderStatusNotFoundException {
        Set<String> value = Set.of("pending", "accepted", "sent", "received");
        if (!value.contains(dto.getStatus())) {
            throw new OrderStatusNotFoundException("error");
        }
        return ResponseEntity.ok(service.status(id, dto.getStatus()));
    }

    /**
     * Выводит список всех даставленных заказов
     *
     * @return возвращает List<OutputOrderNameAdminDto>
     */
    @GetMapping("/received")
    public ResponseEntity<?> receivedOrders() {

        return ResponseEntity.ok(service.list("received"));
    }

    /**
     * возвращает полный список всех заказов
     *
     * @return возвращает
     */
    @GetMapping
    public ResponseEntity<?> ordersAll() {

        return ResponseEntity.ok(service.list("all"));
    }


    /**
     * Выводит все отправленные заказы
     *
     * @return вернет List<OutputOrderNameAdminDto>
     */
    @GetMapping("/sent")
    public ResponseEntity<?> sentOrders() {

        return ResponseEntity.ok(service.list("sent"));
    }

    /**
     * Вернет список принятых заказов
     *
     * @return возвращает List<OutputOrderNameAdminDto>
     */
    @GetMapping("/accepted")
    public ResponseEntity<?> acceptedOrders() {

        return ResponseEntity.ok(service.list("accepted"));
    }

    /**
     * Возвращает список новых заказов.
     *
     * @return Вернет List<OutputOrderNameAdminDto>
     */
    @GetMapping("/pending")
    public ResponseEntity<?> pendingOrders() {

        return ResponseEntity.ok(service.list("pending"));
    }

}
