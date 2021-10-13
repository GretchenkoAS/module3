package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto findOne(Long id);

    List<OrderDto> findAll(int page, int size);

    List<OrderDto> findUserOrders(Long userId, int page, int size);

    OrderDto add(Long userId, Long certificateId);

    void delete(Long id);
}
