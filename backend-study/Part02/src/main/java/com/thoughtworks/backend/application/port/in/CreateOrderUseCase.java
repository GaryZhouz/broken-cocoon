package com.thoughtworks.backend.application.port.in;

import com.thoughtworks.backend.application.domain.model.order.Order;

public interface CreateOrderUseCase {

    Order createOrder(CreateOrderCommand createOrderCommand);

}
