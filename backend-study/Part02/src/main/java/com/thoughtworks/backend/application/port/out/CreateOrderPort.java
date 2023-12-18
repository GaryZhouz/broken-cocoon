package com.thoughtworks.backend.application.port.out;

import com.thoughtworks.backend.application.domain.model.order.Order;
import com.thoughtworks.backend.application.port.in.CreateOrderCommand;

public interface CreateOrderPort {

    Order createOrder(CreateOrderCommand createOrderCommand);

}
