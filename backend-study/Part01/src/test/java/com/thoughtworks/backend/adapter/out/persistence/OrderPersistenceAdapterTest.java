package com.thoughtworks.backend.adapter.out.persistence;

import com.thoughtworks.backend.adapter.out.persistence.mapper.OrderMapper;
import com.thoughtworks.backend.adapter.out.persistence.mapper.OrderProductMapper;
import com.thoughtworks.backend.application.domain.model.order.Order;
import com.thoughtworks.backend.application.domain.model.order.OrderProduct;
import com.thoughtworks.backend.application.port.in.CreateOrderCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
                new CreateOrderCommand.CreateOrderProduct("1", "iPhone 12", 1, "5999"),
                new CreateOrderCommand.CreateOrderProduct("1", "Mac Book Pro 2019", 1, "10999")
        );
        List<OrderProduct> orderProducts = createOrderProducts.stream()
                .map(createOrderProduct -> new OrderProduct(createOrderProduct.id(), createOrderProduct.name(), createOrderProduct.quantity(), createOrderProduct.price()))
                .toList();
        CreateOrderCommand createOrderCommand = new CreateOrderCommand(
                createOrderProducts, "customer-01", "地球", "15566666666"
        );
        Order order = new Order(1L, orderProducts, "16998", Order.OrderStatus.CREATED, LocalDateTime.now(), "customer-01", "地球", "15566666666");

        when(orderPersistenceAdapter.createOrder(createOrderCommand)).thenReturn(order);

        Order result = orderPersistenceAdapter.createOrder(createOrderCommand);

        assertEquals(result, order);

        verify(orderMapper).insert(any());
        verify(orderProductMapper).insertBatchSomeColumn(any());
    }

}
