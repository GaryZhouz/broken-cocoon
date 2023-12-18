package com.thoughtworks.backend.application.domain.service;

import com.thoughtworks.backend.application.domain.model.order.Order;
import com.thoughtworks.backend.application.domain.model.order.OrderProduct;
import com.thoughtworks.backend.application.port.out.GetOrderPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetOrderServiceTest {

    private GetOrderService getOrderService;

    @Mock
    private GetOrderPort getOrderPort;

    @BeforeEach
    void setup() {
        getOrderService = new GetOrderService(getOrderPort);
    }

    @Test
    public void should_return_orders() {
        List<OrderProduct> orderProducts = List.of(
                new OrderProduct("1", "iPhone 12", 1, "5999"),
                new OrderProduct("1", "Mac Book Pro 2019", 1, "10999")
        );
        Order order = new Order(1L, orderProducts, "16998", Order.OrderStatus.CREATED, LocalDateTime.now(), "customer-01", "地球", "15566666666");

        when(getOrderPort.getOrders("1")).thenReturn(List.of(order));

        List<Order> result = getOrderService.getOrders("1");

        assertEquals(result.get(0).getId(), order.getId());
        assertEquals(result.get(0).getTotalPrice(), order.getTotalPrice());
        assertEquals(result.get(0).getStatus(), order.getStatus());

        verify(getOrderPort).getOrders("1");
    }

    @Test
    public void should_return_order_by_order_id() {
        List<OrderProduct> orderProducts = List.of(
                new OrderProduct("1", "iPhone 12", 1, "5999"),
                new OrderProduct("1", "Mac Book Pro 2019", 1, "10999")
        );
        Order order = new Order(1L, orderProducts, "16998", Order.OrderStatus.CREATED, LocalDateTime.now(), "customer-01", "地球", "15566666666");

        when(getOrderPort.getOrder(1L)).thenReturn(order);

        Order result = getOrderService.getOrder(1L);

        assertEquals(result.getId(), order.getId());
        assertEquals(result.getTotalPrice(), order.getTotalPrice());
        assertEquals(result.getStatus(), order.getStatus());

        verify(getOrderPort).getOrder(1L);
    }

}
