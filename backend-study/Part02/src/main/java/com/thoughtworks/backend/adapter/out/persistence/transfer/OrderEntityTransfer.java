package com.thoughtworks.backend.adapter.out.persistence.transfer;

import com.thoughtworks.backend.adapter.out.persistence.entity.OrderEntity;
import com.thoughtworks.backend.adapter.out.persistence.entity.OrderProductEntity;
import com.thoughtworks.backend.application.domain.model.order.Order;
import com.thoughtworks.backend.application.port.in.CreateOrderCommand;

import java.time.LocalDateTime;
import java.util.List;

public class OrderEntityTransfer {

    public static OrderEntity fromCreateOrderCommand(CreateOrderCommand createOrderCommand) {
        return OrderEntity.builder()
                .totalPrice(createOrderCommand.totalPrice())
                .totalDiscountPrice(createOrderCommand.totalDiscountPrice())
                .receivingAddress(createOrderCommand.receivingAddress())
                .receivingNumber(createOrderCommand.receivingNumber())
                .createBy(createOrderCommand.customerId())
                .createTime(LocalDateTime.now())
                .build();
    }

    public static Order toOrderDomain(OrderEntity orderEntity, List<OrderProductEntity> orderProductEntities) {
        return Order.builder()
                .id(orderEntity.getId())
                .products(orderProductEntities.stream()
                        .map(OrderProductEntityTransfer::toOrderProductDomain)
                        .toList())
                .totalPrice(orderEntity.getTotalPrice())
                .totalDiscountPrice(orderEntity.getTotalDiscountPrice())
                .status(orderEntity.getStatus())
                .createTime(orderEntity.getCreateTime())
                .createBy(orderEntity.getCreateBy())
                .receivingAddress(orderEntity.getReceivingAddress())
                .receivingNumber(orderEntity.getReceivingNumber())
                .build();
    }
}
