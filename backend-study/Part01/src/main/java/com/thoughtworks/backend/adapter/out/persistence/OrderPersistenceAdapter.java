package com.thoughtworks.backend.adapter.out.persistence;

import com.thoughtworks.backend.adapter.out.persistence.entity.OrderEntity;
import com.thoughtworks.backend.adapter.out.persistence.entity.OrderProductEntity;
import com.thoughtworks.backend.adapter.out.persistence.mapper.OrderMapper;
import com.thoughtworks.backend.adapter.out.persistence.mapper.OrderProductMapper;
import com.thoughtworks.backend.application.domain.model.order.Order;
import com.thoughtworks.backend.application.port.in.CreateOrderCommand;
import com.thoughtworks.backend.application.port.out.CreateOrderPort;
import com.thoughtworks.backend.common.tag.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.thoughtworks.backend.adapter.out.persistence.transfer.OrderEntityTransfer.fromCreateOrderCommand;
import static com.thoughtworks.backend.adapter.out.persistence.transfer.OrderEntityTransfer.toOrderDomain;
import static com.thoughtworks.backend.adapter.out.persistence.transfer.OrderProductEntityTransfer.fromCreateOrderCommandWithOrderId;

@PersistenceAdapter
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements CreateOrderPort {

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

}
