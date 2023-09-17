package com.thoughtworks.shopbackend.domain.order;

public interface OrderRepository {

    boolean containerNotPaidOrder(Integer userId);

    Integer save(Order order);
}
