package com.thoughtworks.backend.adapter.in.web;

import com.thoughtworks.backend.JUnitWebAppTest;
import com.thoughtworks.backend.application.domain.model.order.Order;
import com.thoughtworks.backend.application.domain.model.order.OrderProduct;
import com.thoughtworks.backend.application.port.in.CreateOrderCommand;
import com.thoughtworks.backend.application.port.in.CreateOrderUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@JUnitWebAppTest
public class CreateOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateOrderUseCase createOrderUseCase;

    @Test
    public void should_create_order_success_when_given_products() throws Exception {
        List<CreateOrderCommand.CreateOrderProduct> createOrderProducts = List.of(
                new CreateOrderCommand.CreateOrderProduct("1", "iPhone 12", 1, "5999"),
                new CreateOrderCommand.CreateOrderProduct("1", "Mac Book Pro 2019", 1, "10999")
        );
        List<OrderProduct> orderProducts = createOrderProducts.stream()
                .map(createOrderProduct -> new OrderProduct(createOrderProduct.id(), createOrderProduct.name(), createOrderProduct.quantity(), createOrderProduct.price()))
                .toList();
        Order order = new Order(1L, orderProducts, "16998", Order.OrderStatus.CREATED, LocalDateTime.now(), "customer-01", "地球", "15566666666");

        when(createOrderUseCase.createOrder(any(CreateOrderCommand.class))).thenReturn(order);

        mockMvc.perform(post("/createOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"products\":[{\"id\":\"1\",\"name\":\"iPhone 12\",\"quantity\":1,\"price\":\"5999\"},{\"id\":\"2\",\"name\":\"Mac Book Pro 2019\",\"quantity\":1,\"price\":\"10999\"}],\"customerId\":\"customer-01\",\"receivingAddress\":\"地球\",\"receivingNumber\":\"15577889988\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.products[*].name", contains("iPhone 12", "Mac Book Pro 2019")))
                .andExpect(jsonPath("$.data.products[*].price", contains("5999", "10999")))
                .andExpect(jsonPath("$.data.status", is("CREATED")));

        verify(createOrderUseCase).createOrder(any(CreateOrderCommand.class));
    }

    @Test
    public void should_create_order_failed_when_given_empty_products() throws Exception {
        mockMvc.perform(post("/createOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":\"customer-01\",\"receivingAddress\":\"地球\",\"receivingNumber\":\"15577889988\"}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_create_order_failed_when_given_invalid_products() throws Exception {
        mockMvc.perform(post("/createOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"products\":[{\"id\":\"1\",\"name\":\"iPhone 12\",\"quantity\":1,\"price\":\"\"},{\"id\":\"2\",\"name\":\"Mac Book Pro 2019\",\"quantity\":1,\"price\":\"10999\"}],\"customerId\":\"customer-01\",\"receivingAddress\":\"地球\",\"receivingNumber\":\"15577889988\"}")
                )
                .andExpect(status().isBadRequest());
    }

}
