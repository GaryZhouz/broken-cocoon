package com.thoughtworks.backend.application.domain.service;

import com.thoughtworks.backend.application.domain.model.product.Product;
import com.thoughtworks.backend.application.port.out.LoadProductsPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetProductsServiceTest {

    private GetProductsService getProductsService;

    @Mock
    private LoadProductsPort loadProductsPort;

    @BeforeEach
    void setup() {
        getProductsService = new GetProductsService(loadProductsPort);
    }

    @Test
    public void should_return_empty_products_when_not_exist_products() {
        when(loadProductsPort.loadProducts()).thenReturn(Collections.emptyList());

        List<Product> products = getProductsService.getProducts();

        assertEquals(products.size(), 0);

        verify(loadProductsPort).loadProducts();
    }

    @Test
    public void should_return_products_when_exist_products() {
        List<Product> products = List.of(
                new Product("1", "iPhone 12", "5999", "5999", 1D, Product.ProductStatus.VALID),
                new Product("2", "Mac Book Pro 2019", "10999", "8249.25", 0.75D, Product.ProductStatus.INVALID)
        );

        when(loadProductsPort.loadProducts()).thenReturn(products);

        List<Product> result = getProductsService.getProducts();

        assertIterableEquals(products, result);

        verify(loadProductsPort).loadProducts();
    }

}
