package com.thoughtworks.shopbackend.api.dto;

import com.thoughtworks.shopbackend.domain.order.Order;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class CreateOrderRequest {

    @Min(value = 1, message = "order sku quantity must gte 1")
    @NotNull
    private Integer quantity;

    @NotNull
    private Integer sku;

    @NotNull
    private Integer buyerId;

    @NotNull
    @Length(max = 255)
    private String address;

    @NotNull
    @Length(max = 32)
    private String fullName;

    @NotNull
    @NotBlank
    @Length(max = 11, min = 11)
    @Pattern(regexp = "^[1][3-9][0-9]{9}$", message = "illegal phone number")
    private String phoneNumber;


    public Order to() {
        return Order.builder()
                .quantity(quantity)
                .sku(sku)
                .buyerId(buyerId)
                .build();
    }
}
