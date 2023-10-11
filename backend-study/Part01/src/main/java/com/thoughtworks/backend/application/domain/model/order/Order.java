package com.thoughtworks.backend.application.domain.model.order;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Order {

    private Long id;

    private List<OrderProduct> products;

    private String totalPrice;

    private OrderStatus status;

    private LocalDate createTime;

    private String createBy;

    private String receivingAddress;

    private String receivingNumber;

    public enum OrderStatus {
        CREATED,
    }

}
