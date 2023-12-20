package com.thoughtworks.backend.application.domain.model.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderProduct {

    private String id;

    private String name;

    private Integer quantity;

    private String price;

    private String discountPrice;

}
