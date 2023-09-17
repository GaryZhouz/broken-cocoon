package com.thoughtworks.shopbackend.infrastructure.repository.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thoughtworks.shopbackend.domain.order.Order;
import com.thoughtworks.shopbackend.domain.order.OrderRepository;
import com.thoughtworks.shopbackend.infrastructure.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderDBEntityRepository orderDBEntityRepository;

    @Override
    public Integer save(Order order) {
        OrderDBEntity orderDBEntity = order.to();
        orderDBEntityRepository.insert(orderDBEntity);
        return orderDBEntity.getOrderId();
    }

    @Override
    public boolean containerNotPaidOrder(Integer userId) {
        QueryWrapper<OrderDBEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("buyer_id", userId)
                .eq("order_status", OrderStatus.NOT_PAID);
        return orderDBEntityRepository.selectCount(queryWrapper) > 0;
    }
}
