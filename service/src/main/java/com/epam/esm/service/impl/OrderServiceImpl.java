package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.exeption.AppException;
import com.epam.esm.exeption.ErrorCode;
import com.epam.esm.mapper.impl.OrderMapper;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final String ID = "id";
    private final OrderDao orderDao;
    private final OrderMapper mapper;
    private final UserService userService;
    private final GiftCertificateService certificateService;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderMapper orderMapper, UserService userService, GiftCertificateService certificateService) {
        this.orderDao = orderDao;
        this.mapper = orderMapper;
        this.certificateService = certificateService;
        this.userService = userService;
    }

    @Override
    public OrderDto findOne(Long id) {
        Optional<Order> optionalOrder = orderDao.findOne(id);
        if (optionalOrder.isEmpty()) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND, ID, id);
        }
        return mapper.mapToDto(optionalOrder.get());
    }

    @Override
    public List<OrderDto> findAll(int page, int size) {
        List<Order> orders = orderDao.findAll(page, size);
        return orders.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findUserOrders(Long userId, int page, int size) {
        List<Order> orders = orderDao.findUserOrders(userId, page, size);
        return orders.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());       }

    @Transactional
    @Override
    public OrderDto add(Long userId, Long certificateId) {
        GiftCertificateDto certificateDto = certificateService.find(certificateId);
        UserDto user = userService.find(userId);
        OrderDto orderDto = new OrderDto();
        orderDto.setCertificate(certificateDto);
        orderDto.setCost(certificateDto.getPrice());
        orderDto.setUser(user);
        Order order = mapper.mapToEntity(orderDto);
        return mapper.mapToDto(orderDao.add(order));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (orderDao.findOne(id).isEmpty()) {
            throw new AppException(ErrorCode.ORDER_NO_CONTENT, ID, id);
        }
        orderDao.delete(id);
    }
}
