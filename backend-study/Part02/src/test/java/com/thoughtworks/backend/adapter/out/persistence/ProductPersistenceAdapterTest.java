package com.thoughtworks.backend.adapter.out.persistence;

import com.thoughtworks.backend.adapter.out.persistence.entity.ProductEntity;
import com.thoughtworks.backend.adapter.out.persistence.mapper.ProductMapper;
import com.thoughtworks.backend.application.domain.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductPersistenceAdapterTest {

    private ProductPersistenceAdapter productPersistenceAdapter;

    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    void setup() {
        productPersistenceAdapter = new ProductPersistenceAdapter(productMapper);
    }

    @Test
    public void should_return_empty_products_when_not_exist_products() {
        when(productMapper.selectList(any())).thenReturn(Collections.emptyList());

        List<Product> products = productPersistenceAdapter.loadProducts();

        assertEquals(products.size(), 0);

        verify(productMapper).selectList(any());
    }

    @Test
    public void should_return_products_when_exist_products() {
        List<Product> products = List.of(
                new Product("1", "iPhone 12", "5999", "5999", Product.ProductStatus.VALID),
                new Product("2", "Mac Book Pro 2019", "10999", "3749.25", Product.ProductStatus.INVALID)
        );

        when(productMapper.selectList(any())).thenReturn(products.stream().map(ProductEntity::from).toList());

        List<Product> result = productPersistenceAdapter.loadProducts();

        assertIterableEquals(products, result);

        verify(productMapper).selectList(any());
    }
}
