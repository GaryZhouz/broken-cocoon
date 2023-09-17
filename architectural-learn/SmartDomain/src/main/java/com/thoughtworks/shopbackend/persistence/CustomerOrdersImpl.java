package com.thoughtworks.shopbackend.persistence;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thoughtworks.shopbackend.domain.customer.CustomerOrders;
import com.thoughtworks.shopbackend.domain.order.Order;
import com.thoughtworks.shopbackend.persistence.db.commodity.CommodityMapper;
import com.thoughtworks.shopbackend.persistence.db.order.OrderDBEntity;
import com.thoughtworks.shopbackend.persistence.db.order.OrderMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomerOrdersImpl implements CustomerOrders {

    private final Integer customerId;

    private final OrderMapper orderMapper;

    private final CommodityMapper commodityMapper;

    @Override
    public List<Order> getCustomerOrders() {
        return orderMapper.selectList(new QueryWrapper<OrderDBEntity>().eq("buyer_id", customerId)).stream()
                .map(OrderDBEntity::to)
                .peek(order -> order.setOrderCommodity(new OrderCommodityImpl(order.getOrderId(), orderMapper, commodityMapper)))
                .collect(Collectors.toList());
    }

    @Override
    public Order createOrder(Order order) {
        orderMapper.insert(OrderDBEntity.from(order));
        return order;
    }
}
