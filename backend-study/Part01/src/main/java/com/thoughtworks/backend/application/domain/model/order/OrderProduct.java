package com.thoughtworks.backend.application.domain.model.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderProduct {

    private String id;

    private String name;

    private Long quantity;

    private String price;

}
