package com.thoughtworks.backend.adapter.out.persistence;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thoughtworks.backend.adapter.out.persistence.entity.OrderEntity;
import com.thoughtworks.backend.adapter.out.persistence.entity.OrderProductEntity;
import com.thoughtworks.backend.adapter.out.persistence.mapper.OrderMapper;
import com.thoughtworks.backend.adapter.out.persistence.mapper.OrderProductMapper;
import com.thoughtworks.backend.application.domain.model.order.Order;
import com.thoughtworks.backend.application.port.in.CreateOrderCommand;
import com.thoughtworks.backend.application.port.out.CreateOrderPort;
import com.thoughtworks.backend.application.port.out.GetOrderPort;
import com.thoughtworks.backend.common.exception.BusinessException;
import com.thoughtworks.backend.common.exception.CommonErrorCode;
import com.thoughtworks.backend.common.tag.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.thoughtworks.backend.adapter.out.persistence.transfer.OrderEntityTransfer.fromCreateOrderCommand;
import static com.thoughtworks.backend.adapter.out.persistence.transfer.OrderEntityTransfer.toOrderDomain;
import static com.thoughtworks.backend.adapter.out.persistence.transfer.OrderProductEntityTransfer.fromCreateOrderCommandWithOrderId;

@PersistenceAdapter
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements CreateOrderPort, GetOrderPort {

    private final OrderMapper orderMapper;

    private final OrderProductMapper orderProductMapper;

    @Override
    @Transactional
    public Order createOrder(CreateOrderCommand createOrderCommand) {
        OrderEntity orderEntity = fromCreateOrderCommand(createOrderCommand);
        orderMapper.insert(orderEntity);
        List<OrderProductEntity> orderProductEntities = fromCreateOrderCommandWithOrderId(orderEntity.getId(), createOrderCommand);
        orderProductMapper.insertBatchSomeColumn(orderProductEntities);
        return toOrderDomain(orderEntity, orderProductEntities);
    }

    @Override
    public List<Order> getOrders(String customerId) {
        List<OrderEntity> orderEntities = orderMapper.selectList(new QueryWrapper<OrderEntity>().eq("create_by", customerId));
        if (orderEntities.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> orderIds = orderEntities.stream()
                .map(OrderEntity::getId)
                .toList();
        List<OrderProductEntity> orderProductEntities = orderProductMapper.selectList(new QueryWrapper<OrderProductEntity>().in("order_id", orderIds));
        return orderEntities
                .stream()
                .map(orderEntity -> toOrderDomain(orderEntity, orderProductEntities))
                .toList();
    }

    @Override
    public Order getOrder(Long orderId) {
        OrderEntity orderEntity = orderMapper.selectById(orderId);
        if (orderEntity == null) {
            throw new BusinessException(CommonErrorCode.NOT_FOUND);
        }
        List<OrderProductEntity> orderProductEntities = orderProductMapper.selectList(new QueryWrapper<OrderProductEntity>().eq("order_id", orderId));
        return toOrderDomain(orderEntity, orderProductEntities);
    }
}
