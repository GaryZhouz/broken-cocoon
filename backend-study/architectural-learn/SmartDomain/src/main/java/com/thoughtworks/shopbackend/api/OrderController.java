package com.thoughtworks.shopbackend.api;

import com.thoughtworks.shopbackend.api.components.model.JsonResult;
import com.thoughtworks.shopbackend.api.dto.CreateOrderRequest;
import com.thoughtworks.shopbackend.domain.customer.Customer;
import com.thoughtworks.shopbackend.domain.customer.Customers;
import com.thoughtworks.shopbackend.domain.exception.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.thoughtworks.shopbackend.api.Constant.ORDER_API;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ORDER_API)
public class OrderController {

    private final Customers customers;

    @PostMapping("create")
    public JsonResult<Integer> create(@RequestBody @Valid CreateOrderRequest orderRequest) {
        return JsonResult.<Integer>builder()
                .data(
                        customers.getCustomer(orderRequest.getBuyerId())
                                .map(customer -> customer.getOrders().createOrder(orderRequest.to())
                                        .getOrderId())
                                .orElseThrow(() -> new RecordNotFoundException(orderRequest.getBuyerId(), Customer.class))
                )
                .build();
    }
}
