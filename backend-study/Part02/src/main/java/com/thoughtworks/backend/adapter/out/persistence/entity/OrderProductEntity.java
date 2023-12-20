package com.thoughtworks.backend.adapter.out.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_order_product")
public class OrderProductEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private String productId;

    private String productName;

    private String productPrice;

    private String productDiscountPrice;

    private Integer quantity;

}
