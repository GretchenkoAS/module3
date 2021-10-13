package com.epam.esm.mapper.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper implements Mapper<Order, OrderDto> {
    private final GiftCertificateMapper giftCertificateMapper;
    private final UserMapper userMapper;

    @Autowired
    public OrderMapper(GiftCertificateMapper giftCertificateMapper, UserMapper userMapper) {
        this.giftCertificateMapper = giftCertificateMapper;
        this.userMapper = userMapper;
    }

    @Override
    public OrderDto mapToDto(Order entity) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(entity.getId());
        orderDto.setCost(entity.getCost());
        orderDto.setPurchaseDate(entity.getPurchaseDate());
        if (entity.getCertificate() != null) {
            orderDto.setCertificate(giftCertificateMapper.mapToDto(entity.getCertificate()));
        }
        if (entity.getUser() != null) {
            orderDto.setUser(userMapper.mapToDto(entity.getUser()));
        }
        return orderDto;
    }

    @Override
    public Order mapToEntity(OrderDto dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setCost(dto.getCost());
        order.setPurchaseDate(dto.getPurchaseDate());
        if (dto.getCertificate() != null) {
            order.setCertificate(giftCertificateMapper.mapToEntity(dto.getCertificate()));
        }
        if (dto.getUser() != null) {
            order.setUser(userMapper.mapToEntity(dto.getUser()));
        }
        return order;
    }
}
