package com.thoughtworks.shopbackend.domain.customer;

import com.thoughtworks.shopbackend.domain.order.Order;

import java.util.List;

public interface CustomerOrders {

    List<Order> getCustomerOrders();

    Order createOrder(Order order);

}
