package com.thoughtworks.shopbackend.api;

import com.thoughtworks.shopbackend.api.request.CreateOrderRequest;
import com.thoughtworks.shopbackend.application.order.OrderService;
import com.thoughtworks.shopbackend.application.order.dto.OrderDTO;
import com.thoughtworks.shopbackend.infrastructure.model.JsonResult;
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

    private final OrderService orderService;

    @PostMapping("create")
    public JsonResult<OrderDTO> create(@RequestBody @Valid CreateOrderRequest orderRequest) {
        return JsonResult.<OrderDTO>builder()
                .data(orderService.create(orderRequest.toDto()))
                .build();
    }
}
