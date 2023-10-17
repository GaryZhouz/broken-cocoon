package com.thoughtworks.backend.adapter.in.web;

import com.thoughtworks.backend.application.domain.model.order.Order;
import com.thoughtworks.backend.application.port.in.CreateOrderCommand;
import com.thoughtworks.backend.application.port.in.CreateOrderUseCase;
import com.thoughtworks.backend.common.model.JsonResult;
import com.thoughtworks.backend.common.tag.WebAdapter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class CreateOrderController {

    private final CreateOrderUseCase createOrderUseCase;

    @PostMapping("/createOrder")
    public JsonResult<Order> getProducts(@Valid @RequestBody CreateOrderCommand createOrderCommand) {
        return JsonResult.of(createOrderUseCase.createOrder(createOrderCommand));
    }

}
