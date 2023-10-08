package com.thoughtworks.backend.controller.response;

import com.thoughtworks.backend.entity.Customer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerProfileResponse {

    private String name;

    public static CustomerProfileResponse from(Customer customer) {
        return CustomerProfileResponse.builder()
                .name(customer.getName())
                .build();
    }

}
