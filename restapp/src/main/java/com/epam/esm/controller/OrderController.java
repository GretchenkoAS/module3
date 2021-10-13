package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> findAll(@RequestParam(required = false, defaultValue = "1") int page,
                                                  @RequestParam(required = false, defaultValue = "5") int size) {
        List<OrderDto> orders = orderService.findAll(page, size);
        for (OrderDto order : orders) {
            addLinks(order);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping(params = {"userId"})
    public ResponseEntity<List<OrderDto>> findUserOrders(@RequestParam Long userId,
                                                         @RequestParam(required = false, defaultValue = "1") int page,
                                                         @RequestParam(required = false, defaultValue = "5") int size) {
        List<OrderDto> orders = orderService.findUserOrders(userId, page, size);
        for (OrderDto order : orders) {
            addLinks(order);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable Long id) {
        OrderDto order = orderService.findOne(id);
        addLinks(order);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @PostMapping
    public ResponseEntity<OrderDto> add(@RequestParam Long userId,
                                        @RequestParam Long certificateId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.add(userId, certificateId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    private void addLinks(OrderDto orderDto){
        orderDto.add(linkTo(methodOn(OrderController.class).findById(orderDto.getId())).withSelfRel());
        orderDto.add(linkTo(methodOn(OrderController.class).add(0L, 0L)).withRel("create"));
        orderDto.add(linkTo(methodOn(OrderController.class).delete(orderDto.getId())).withRel("delete"));
    }
}
