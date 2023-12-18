package com.thoughtworks.backend.adapter.in.web;

import com.thoughtworks.backend.application.domain.model.order.Order;
import com.thoughtworks.backend.application.port.in.GetOrderUseCase;
import com.thoughtworks.backend.common.model.JsonResult;
import com.thoughtworks.backend.common.tag.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class GetOrderController {

    private final GetOrderUseCase getOrderUseCase;

    @GetMapping("/{customerId}/orders")
    public JsonResult<List<Order>> getOrders(@PathVariable String customerId) {
        return JsonResult.of(getOrderUseCase.getOrders(customerId));
    }

    @GetMapping("/{orderId}/order")
    public JsonResult<Order> getOrderDetail(@PathVariable Long orderId) {
        return JsonResult.of(getOrderUseCase.getOrder(orderId));
    }

}
