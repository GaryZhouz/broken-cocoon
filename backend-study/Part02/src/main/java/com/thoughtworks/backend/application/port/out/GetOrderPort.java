package com.thoughtworks.backend.application.port.out;

import com.thoughtworks.backend.application.domain.model.order.Order;

import java.util.List;

public interface GetOrderPort {

    List<Order> getOrders(String customerId);

    Order getOrder(Long orderId);

}
