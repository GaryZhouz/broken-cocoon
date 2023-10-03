package com.thoughtworks.shopbackend.service.order.dto;

import com.thoughtworks.shopbackend.service.commodity.dto.CommodityDTO;
import com.thoughtworks.shopbackend.dto.OrderDto;
import com.thoughtworks.shopbackend.components.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class OrderDTO {
    private Integer orderId;

    private Integer quantity;

    private BigDecimal totalPrice;

    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.NOT_PAID;

    private CommodityDTO commodity;

    private Integer buyerId;

    private Date createAt;

    private Integer addressId;

    private String address;

    private String fullName;

    private String phoneNumber;

    public BigDecimal getTotalPrice() {
        return BigDecimal.valueOf(quantity)
                .multiply(commodity.getAmount());
    }

    public OrderDto to() {
        return OrderDto.builder()
                .sku(commodity.getSku())
                .buyerId(buyerId)
                .totalPrice(getTotalPrice())
                .quantity(quantity)
                .createAt(createAt)
                .addressId(addressId)
                .orderStatus(orderStatus)
                .build();
    }
}
