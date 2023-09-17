package com.thoughtworks.shopbackend.infrastructure.repository.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.thoughtworks.shopbackend.infrastructure.model.OrderStatus;
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

    private Integer addressId;

}
