package com.thoughtworks.shopbackend.domain.order;

import com.thoughtworks.shopbackend.infrastructure.repository.order.OrderDBEntity;
import com.thoughtworks.shopbackend.infrastructure.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class Order {

    private Integer orderId;

    private Integer quantity;

    private BigDecimal totalPrice;

    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.NOT_PAID;

    private Integer sku;

    private Integer buyerId;

    private Date createAt;

    private Integer addressId;

    public OrderDBEntity to() {
        return OrderDBEntity.builder()
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
