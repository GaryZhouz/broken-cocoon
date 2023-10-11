package com.thoughtworks.backend.adapter.out.persistence;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thoughtworks.backend.adapter.out.persistence.entity.ProductEntity;
import com.thoughtworks.backend.adapter.out.persistence.mapper.ProductMapper;
import com.thoughtworks.backend.application.domain.model.product.Product;
import com.thoughtworks.backend.application.port.out.LoadProductsPort;
import com.thoughtworks.backend.common.tag.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements LoadProductsPort {

    private final ProductMapper productMapper;

    @Override
    public List<Product> loadProducts() {
        return productMapper.selectList(new QueryWrapper<>())
                .stream()
                .map(ProductEntity::to)
                .toList();
    }

}
