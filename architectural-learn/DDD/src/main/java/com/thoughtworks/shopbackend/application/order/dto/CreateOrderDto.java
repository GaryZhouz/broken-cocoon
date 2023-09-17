package com.thoughtworks.shopbackend.application.order.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Builder
public class CreateOrderDto {

    private Integer quantity;

    private Integer sku;

    private Integer buyerId;

    private String address;

    private String fullName;

    private String phoneNumber;

    public OrderDTO to() {
        return OrderDTO.builder()
                .buyerId(buyerId)
                .address(address)
                .fullName(fullName)
                .quantity(quantity)
                .phoneNumber(phoneNumber)
                .createAt(new Date())
                .build();
    }
}
