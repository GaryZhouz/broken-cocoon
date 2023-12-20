package com.thoughtworks.backend.adapter.in.web;

import com.thoughtworks.backend.application.domain.model.order.Order;
import com.thoughtworks.backend.application.domain.model.order.OrderProduct;
import com.thoughtworks.backend.application.port.in.GetOrderUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GetOrderController.class)
public class GetOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetOrderUseCase getOrderUseCase;

    @Test
    public void should_return_orders() throws Exception {
        List<OrderProduct> orderProducts = List.of(
                new OrderProduct("1", "iPhone 12", 1, "5999", "5999"),
                new OrderProduct("1", "Mac Book Pro 2019", 1, "10999", "8249.25")
        );
        Order order = new Order(1L, orderProducts, "16998", "14248.25", Order.OrderStatus.CREATED, LocalDateTime.now(), "customer-01", "地球", "15566666666");

        when(getOrderUseCase.getOrders("1")).thenReturn(List.of(order));

        mockMvc.perform(get("/1/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[*].id", contains(1)))
                .andExpect(jsonPath("$.data[*].products[*].name", contains("iPhone 12", "Mac Book Pro 2019")))
                .andExpect(jsonPath("$.data[*].products[*].price", contains("5999", "10999")))
                .andExpect(jsonPath("$.data[*].status", contains("CREATED")))
                .andExpect(jsonPath("$.data[*].totalPrice", contains("16998")))
                .andExpect(jsonPath("$.data[*].totalDiscountPrice", contains("14248.25")));

        verify(getOrderUseCase).getOrders("1");
    }

    @Test
    public void should_return_order_when_given_order_id() throws Exception {
        List<OrderProduct> orderProducts = List.of(
                new OrderProduct("1", "iPhone 12", 1, "5999", "5999"),
                new OrderProduct("1", "Mac Book Pro 2019", 1, "10999", "8249.25")
        );
        Order order = new Order(1L, orderProducts, "16998", "14248.25", Order.OrderStatus.CREATED, LocalDateTime.now(), "customer-01", "地球", "15566666666");

        when(getOrderUseCase.getOrder(1L)).thenReturn(order);

        mockMvc.perform(get("/1/order")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.products[*].name", contains("iPhone 12", "Mac Book Pro 2019")))
                .andExpect(jsonPath("$.data.products[*].price", contains("5999", "10999")))
                .andExpect(jsonPath("$.data.status", is("CREATED")))
                .andExpect(jsonPath("$.data.totalPrice", is("16998")))
                .andExpect(jsonPath("$.data.totalDiscountPrice", is("14248.25")));

        verify(getOrderUseCase).getOrder(1L);
    }

}
