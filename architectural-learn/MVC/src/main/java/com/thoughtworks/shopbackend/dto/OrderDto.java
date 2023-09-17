package com.thoughtworks.shopbackend.dto;

import com.thoughtworks.shopbackend.entity.OrderEntity;
import com.thoughtworks.shopbackend.components.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class OrderDto {

    private Integer orderId;

    private Integer quantity;

    private BigDecimal totalPrice;

    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.NOT_PAID;

    private Integer sku;

    private Integer buyerId;

    private Date createAt;

    private Integer addressId;

    public OrderEntity to() {
        return OrderEntity.builder()
                .addressId(addressId)
                .buyerId(buyerId)
                .createAt(createAt)
                .totalPrice(totalPrice)
                .quantity(quantity)
                .sku(sku)
                .orderStatus(orderStatus)
                .build();
    }
}
