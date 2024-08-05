//package de.shop.modules.order.controller;
//
//import de.shop.modules.order.domain.dto.OrderDto;
//import de.shop.modules.order.service.OrderService;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/available_orders")
//public class OrderController {
//    private final OrderService service;
//
//    // uhjgfjh
//
//    public OrderController(OrderService service) {
//        this.service = service;
//    }
//
//    @PostMapping
//    public ResponseDto<OrderDto> save(@RequestBody OrderDto order) {
//        return service.save(order);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteById(@PathVariable Long id) {
//        return service.deleteById(id);
//    }
//
//    @PutMapping
//    public ResponseDto<OrderDto> update(OrderDto order) {
//        return service.update(order);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseDto<?> findById(@PathVariable Long id) {
//        return service.findById(id);
//    }
//
//}
