package com.thoughtworks.shopbackend.dto;

import com.thoughtworks.shopbackend.entity.ShippingAddressEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShippingAddressDto {

    private Integer id;

    private String address;

    private String fullName;

    private String phoneNumber;

    private Integer customerId;

    public ShippingAddressEntity to() {
        return ShippingAddressEntity.builder()
                .address(address)
                .fullName(fullName)
                .customerId(customerId)
                .phoneNumber(phoneNumber)
                .build();
    }
}
