package com.epam.esm.dao;

import com.epam.esm.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Optional<Order> findOne(Long id);

    List<Order> findAll(int page, int size);

    List<Order> findUserOrders(Long userId, int page, int size);

    Order add(Order order);

    void delete(Long id);
}
