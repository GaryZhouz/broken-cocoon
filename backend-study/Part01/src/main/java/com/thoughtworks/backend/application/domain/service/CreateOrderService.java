package com.thoughtworks.backend.application.domain.service;

import com.thoughtworks.backend.application.domain.model.order.Order;
import com.thoughtworks.backend.application.port.in.CreateOrderCommand;
import com.thoughtworks.backend.application.port.in.CreateOrderUseCase;
import com.thoughtworks.backend.application.port.out.CreateOrderPort;
import com.thoughtworks.backend.common.tag.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateOrderService implements CreateOrderUseCase {

    private final CreateOrderPort createOrderPort;

    @Override
    public Order createOrder(CreateOrderCommand createOrderCommand) {
        return createOrderPort.createOrder(createOrderCommand);
    }

}
