package com.thoughtworks.backend.application.domain.service;

import com.thoughtworks.backend.application.domain.model.order.Order;
import com.thoughtworks.backend.application.domain.model.order.OrderProduct;
import com.thoughtworks.backend.application.port.in.CreateOrderCommand;
import com.thoughtworks.backend.application.port.out.CreateOrderPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CreateOrderServiceTest {

    private CreateOrderService createOrderService;

    @Mock
    private CreateOrderPort createOrderPort;

    @BeforeEach
    void setup() {
        createOrderService = new CreateOrderService(createOrderPort);
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
        Order order = new Order(1L, orderProducts, "16998", "14248.25", Order.OrderStatus.CREATED, LocalDateTime.now(), "customer-01", "地球", "15566666666");

        when(createOrderPort.createOrder(createOrderCommand)).thenReturn(order);

        Order result = createOrderService.createOrder(createOrderCommand);

        assertEquals(result, order);

        verify(createOrderPort).createOrder(createOrderCommand);
    }


}
