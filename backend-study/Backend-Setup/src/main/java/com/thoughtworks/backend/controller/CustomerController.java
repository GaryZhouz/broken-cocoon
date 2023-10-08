package com.thoughtworks.backend.controller;

import com.thoughtworks.backend.controller.response.CustomerProfileResponse;
import com.thoughtworks.backend.model.JsonResult;
import com.thoughtworks.backend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}/profile")
    public JsonResult<CustomerProfileResponse> getCustomerProfile(@PathVariable Integer id) {
        return JsonResult.<CustomerProfileResponse>builder()
                .data(CustomerProfileResponse.from(customerService.getById(id)))
                .build();
    }
}
