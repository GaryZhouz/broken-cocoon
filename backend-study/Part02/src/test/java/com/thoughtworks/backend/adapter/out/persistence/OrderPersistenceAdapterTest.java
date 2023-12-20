package com.thoughtworks.backend.adapter.out.persistence;

import com.thoughtworks.backend.adapter.out.persistence.entity.OrderEntity;
import com.thoughtworks.backend.adapter.out.persistence.entity.OrderProductEntity;
import com.thoughtworks.backend.adapter.out.persistence.mapper.OrderMapper;
import com.thoughtworks.backend.adapter.out.persistence.mapper.OrderProductMapper;
import com.thoughtworks.backend.application.domain.model.order.Order;
import com.thoughtworks.backend.application.domain.model.order.OrderProduct;
import com.thoughtworks.backend.application.port.in.CreateOrderCommand;
import com.thoughtworks.backend.common.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderPersistenceAdapterTest {

    private OrderPersistenceAdapter orderPersistenceAdapter;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderProductMapper orderProductMapper;

    @BeforeEach
    void setup() {
        orderPersistenceAdapter = new OrderPersistenceAdapter(orderMapper, orderProductMapper);
    }

    @Test
    public void should_create_order_success() {
        List<CreateOrderCommand.CreateOrderProduct> createOrderProducts = List.of(
                new CreateOrderCommand.CreateOrderProduct("1", "iPhone 12", 1, "5999", "5999"),
                new CreateOrderCommand.CreateOrderProduct("1", "Mac Book Pro 2019", 1, "10999", "8249.25")
        );
        List<OrderProduct> orderProducts = createOrderProducts.stream()
                .map(createOrderProduct -> new OrderProduct(createOrderProduct.id(), createOrderProduct.name(), createOrderProduct.quantity(), createOrderProduct.price(), createOrderProduct.discountPrice()))
                .toList();
        CreateOrderCommand createOrderCommand = new CreateOrderCommand(
                createOrderProducts, "customer-01", "地球", "15566666666"
        );
        Order order = new Order(1L, orderProducts, "16998.0", "14248.25", Order.OrderStatus.CREATED, LocalDateTime.now(), "customer-01", "地球", "15566666666");

        when(orderMapper.insert(any())).thenReturn(1);
        when(orderProductMapper.insertBatchSomeColumn(any())).thenReturn(1);

        Order result = orderPersistenceAdapter.createOrder(createOrderCommand);

        assertEquals(result.getCreateBy(), order.getCreateBy());
        assertEquals(result.getTotalPrice(), order.getTotalPrice());
        assertEquals(result.getTotalDiscountPrice(), order.getTotalDiscountPrice());
        assertEquals(result.getStatus(), order.getStatus());

        verify(orderMapper).insert(any());
        verify(orderProductMapper).insertBatchSomeColumn(any());
    }

    @Test
    public void should_return_orders() {
        List<OrderProductEntity> orderProducts = List.of(
                new OrderProductEntity(1L, 1L, "1", "iPhone 12", "5999", "5999", 1),
                new OrderProductEntity(1L, 1L, "1", "Mac Book Pro 2019", "10999", "8249.25", 1)
        );
        OrderEntity orderEntity = new OrderEntity(1L, "16998", "14248.25", Order.OrderStatus.CREATED, LocalDateTime.now(), "customer-01", "地球", "15566666666");

        when(orderMapper.selectList(any())).thenReturn(List.of(orderEntity));
        when(orderProductMapper.selectList(any())).thenReturn(orderProducts);

        List<Order> result = orderPersistenceAdapter.getOrders("1");

        assertEquals(result.get(0).getId(), orderEntity.getId());
        assertEquals(result.get(0).getTotalPrice(), orderEntity.getTotalPrice());
        assertEquals(result.get(0).getStatus(), orderEntity.getStatus());

        verify(orderMapper).selectList(any());
        verify(orderProductMapper).selectList(any());
    }

    @Test
    public void should_return_order_by_order_id() {
        OrderEntity orderEntity = new OrderEntity(1L, "16998", "14248.25", Order.OrderStatus.CREATED, LocalDateTime.now(), "customer-01", "地球", "15566666666");

        when(orderMapper.selectById(any())).thenReturn(orderEntity);

        Order result = orderPersistenceAdapter.getOrder(1L);

        assertEquals(result.getId(), orderEntity.getId());
        assertEquals(result.getTotalPrice(), orderEntity.getTotalPrice());
        assertEquals(result.getStatus(), orderEntity.getStatus());

        verify(orderMapper).selectById(any());
    }

    @Test
    public void should_throw_exception_when_order_id_not_exist() {
        when(orderMapper.selectById(any())).thenReturn(null);

        assertThrows(BusinessException.class, () -> orderPersistenceAdapter.getOrder(1L));

        verify(orderMapper).selectById(any());
    }

}
