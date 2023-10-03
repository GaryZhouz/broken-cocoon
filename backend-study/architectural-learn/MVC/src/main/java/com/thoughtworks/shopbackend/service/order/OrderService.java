package com.thoughtworks.shopbackend.service.order;

import com.thoughtworks.shopbackend.dto.OrderDto;

public interface OrderService {

    boolean containerNotPaidOrder(Integer userId);

    Integer save(OrderDto orderDto);
}
