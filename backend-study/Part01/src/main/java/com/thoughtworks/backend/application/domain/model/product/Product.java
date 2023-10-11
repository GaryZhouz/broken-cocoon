package com.thoughtworks.backend.application.domain.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
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
