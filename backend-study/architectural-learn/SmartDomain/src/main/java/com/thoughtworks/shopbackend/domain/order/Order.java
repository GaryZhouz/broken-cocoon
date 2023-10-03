package com.thoughtworks.shopbackend.domain.order;

import com.thoughtworks.shopbackend.domain.customer.ShoppingAddressValueObject;
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

    private ShoppingAddressValueObject shoppingAddress;

    private OrderCommodity orderCommodity;

}
