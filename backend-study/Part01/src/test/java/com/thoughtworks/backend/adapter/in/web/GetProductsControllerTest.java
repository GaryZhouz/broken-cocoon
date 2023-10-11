package com.thoughtworks.backend.adapter.in.web;

import com.thoughtworks.backend.JUnitWebAppTest;
import com.thoughtworks.backend.application.domain.model.product.Product;
import com.thoughtworks.backend.application.port.in.GetProductsUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@JUnitWebAppTest
public class GetProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetProductsUseCase getProductsUseCase;

    @Test
    public void should_return_empty_products_when_not_exist_products() throws Exception {
        when(getProductsUseCase.getProducts()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/getProducts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty());

        verify(getProductsUseCase).getProducts();
    }

    @Test
    public void should_return_products_when_exist_products() throws Exception {
        List<Product> products = List.of(
                new Product("1", "iPhone 12", "5999", Product.ProductStatus.VALID),
                new Product("2", "Mac Book Pro 2019", "10999", Product.ProductStatus.INVALID)
        );

        when(getProductsUseCase.getProducts()).thenReturn(products);

        mockMvc.perform(get("/getProducts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[*].id", contains("1", "2")))
                .andExpect(jsonPath("$.data[*].name", contains("iPhone 12", "Mac Book Pro 2019")))
                .andExpect(jsonPath("$.data[*].price", contains("5999", "10999")))
                .andExpect(jsonPath("$.data[*].status", contains("VALID", "INVALID")));

        verify(getProductsUseCase).getProducts();
    }

}
