package com.thoughtworks.backend.adapter.out.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.thoughtworks.backend.application.domain.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_product")
public class ProductEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String name;

    private String price;

    private Product.ProductStatus status;

    public Product to() {
        return Product.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .status(this.status)
                .build();
    }

    public static ProductEntity from(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .status(product.getStatus())
                .build();
    }

}
