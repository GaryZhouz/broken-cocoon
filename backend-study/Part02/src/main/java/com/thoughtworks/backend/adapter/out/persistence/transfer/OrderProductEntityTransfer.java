package com.thoughtworks.backend.adapter.out.persistence.transfer;

import com.thoughtworks.backend.adapter.out.persistence.entity.OrderProductEntity;
import com.thoughtworks.backend.application.domain.model.order.OrderProduct;
import com.thoughtworks.backend.application.port.in.CreateOrderCommand;

import java.util.List;

public class OrderProductEntityTransfer {

    public static List<OrderProductEntity> fromCreateOrderCommandWithOrderId(Long orderId, CreateOrderCommand createOrderCommand) {
        return createOrderCommand.products()
                .stream()
                .map(createOrderProduct -> OrderProductEntity
                        .builder()
                        .orderId(orderId)
                        .productId(createOrderProduct.id())
                        .productName(createOrderProduct.name())
                        .productPrice(createOrderProduct.price())
                        .quantity(createOrderProduct.quantity())
                        .build()).toList();
    }

    public static OrderProduct toOrderProductDomain(OrderProductEntity orderProductEntity) {
        return OrderProduct.builder()
                .id(orderProductEntity.getProductId())
                .name(orderProductEntity.getProductName())
                .price(orderProductEntity.getProductPrice())
                .quantity(orderProductEntity.getQuantity())
                .build();
    }
}
