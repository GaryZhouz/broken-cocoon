package com.thoughtworks.shopbackend.domain.address;

import com.thoughtworks.shopbackend.infrastructure.repository.address.ShippingAddressDBEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShippingAddress {

    private Integer id;

    private String address;

    private String fullName;

    private String phoneNumber;

    private Integer customerId;

    public ShippingAddressDBEntity to() {
        return ShippingAddressDBEntity.builder()
                .address(address)
                .fullName(fullName)
                .customerId(customerId)
                .phoneNumber(phoneNumber)
                .build();
    }
}
