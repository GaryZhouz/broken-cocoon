package com.thoughtworks.shopbackend.domain.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShoppingAddress {

    private Integer id;

    private String address;

    private String fullName;

    private String phoneNumber;

}
