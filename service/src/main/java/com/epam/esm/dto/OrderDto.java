package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class OrderDto extends RepresentationModel<OrderDto> {
    private Long id;
    private BigDecimal cost;
    private LocalDateTime purchaseDate;
    private GiftCertificateDto certificate;
    private UserDto user;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public GiftCertificateDto getCertificate() {
        return certificate;
    }

    public void setCertificate(GiftCertificateDto certificate) {
        this.certificate = certificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(id, orderDto.id) && Objects.equals(cost, orderDto.cost) && Objects.equals(purchaseDate, orderDto.purchaseDate) && Objects.equals(certificate, orderDto.certificate) && Objects.equals(user, orderDto.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, cost, purchaseDate, certificate, user);
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", cost=" + cost +
                ", purchaseDate=" + purchaseDate +
                ", certificate=" + certificate +
                ", user=" + user +
                '}';
    }
}
