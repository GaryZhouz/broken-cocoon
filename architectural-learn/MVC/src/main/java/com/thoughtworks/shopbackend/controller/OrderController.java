package com.thoughtworks.shopbackend.controller;

import com.thoughtworks.shopbackend.controller.request.CreateOrderRequest;
import com.thoughtworks.shopbackend.service.order.OrderServiceImpl;
import com.thoughtworks.shopbackend.service.order.dto.OrderDTO;
import com.thoughtworks.shopbackend.components.model.JsonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.thoughtworks.shopbackend.controller.Constant.ORDER_API;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ORDER_API)
public class OrderController {

    private final OrderServiceImpl orderServiceImpl;

    @PostMapping("create")
    public JsonResult<OrderDTO> create(@RequestBody @Valid CreateOrderRequest orderRequest) {
        return JsonResult.<OrderDTO>builder()
                .data(orderServiceImpl.create(orderRequest))
                .build();
    }
}
