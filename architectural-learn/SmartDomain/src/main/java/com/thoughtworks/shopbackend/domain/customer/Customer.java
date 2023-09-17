package com.thoughtworks.shopbackend.domain.customer;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Customer {

    private Integer customerId;

    private CustomerShoppingAddress customerShoppingAddress;

    private CustomerOrders orders;

}
