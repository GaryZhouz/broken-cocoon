package com.thoughtworks.backend.adapter.out.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.thoughtworks.backend.application.domain.model.order.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_order")
public class OrderEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String totalPrice;

    @Builder.Default
    private Order.OrderStatus status = Order.OrderStatus.CREATED;

    private LocalDateTime createTime;

    private String createBy;

    private String receivingAddress;

    private String receivingNumber;

}
