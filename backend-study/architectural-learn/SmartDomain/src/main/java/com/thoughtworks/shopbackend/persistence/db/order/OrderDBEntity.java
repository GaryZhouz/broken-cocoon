package com.thoughtworks.shopbackend.persistence.db.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.thoughtworks.shopbackend.domain.order.Order;
import com.thoughtworks.shopbackend.domain.order.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@TableName("t_order")
public class OrderDBEntity {

    @TableId(type = IdType.AUTO)
    private Integer orderId;

    private Integer quantity;

    private BigDecimal totalPrice;

    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.NOT_PAID;

    private Integer sku;

    private Integer buyerId;

    private Date createAt;

    private String address;

    public static OrderDBEntity from(Order order) {
        return OrderDBEntity.builder()
                .orderId(order.getOrderId())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .sku(order.getSku())
                .buyerId(order.getBuyerId())
                .createAt(new Date())
                .build();
    }

    public Order to() {
        return Order.builder()
                .orderId(this.getOrderId())
                .quantity(this.getQuantity())
                .orderStatus(this.getOrderStatus())
                .totalPrice(this.getTotalPrice())
                .sku(this.getSku())
                .buyerId(this.getBuyerId())
                .createAt(new Date())
                .address(this.getAddress())
                .build();
    }

}
