package com.thoughtworks.backend.application.domain.service;

import com.thoughtworks.backend.application.domain.model.order.Order;
import com.thoughtworks.backend.application.port.in.GetOrderUseCase;
import com.thoughtworks.backend.application.port.out.GetOrderPort;
import com.thoughtworks.backend.common.tag.UseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetOrderService implements GetOrderUseCase {

    private final GetOrderPort getOrderPort;

    @Override
    public List<Order> getOrders(String customerId) {
        return getOrderPort.getOrders(customerId);
    }

    @Override
    public Order getOrder(Long orderId) {
        return getOrderPort.getOrder(orderId);
    }

}
