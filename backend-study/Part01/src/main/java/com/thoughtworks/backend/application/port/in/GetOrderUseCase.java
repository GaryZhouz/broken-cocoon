package com.thoughtworks.backend.application.port.in;

import com.thoughtworks.backend.application.domain.model.order.Order;

import java.util.List;

public interface GetOrderUseCase {

    List<Order> getOrders(String customerId);

    Order getOrder(Long orderId);

}
