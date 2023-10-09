package com.thoughtworks.backend.application.domain.model.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    private String id;

    private String name;

    private String price;

    private ProductStatus status;

    public enum ProductStatus {
        INVALID,
        VALID
    }
}
