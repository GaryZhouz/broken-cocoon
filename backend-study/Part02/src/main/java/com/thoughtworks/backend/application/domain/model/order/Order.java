package com.thoughtworks.backend.application.domain.model.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Order {

    private Long id;

    private List<OrderProduct> products;

    private String totalPrice;

    private String totalDiscountPrice;

    private OrderStatus status;

    private LocalDateTime createTime;

    private String createBy;

    private String receivingAddress;

    private String receivingNumber;

    public enum OrderStatus {
        CREATED,
    }

}
