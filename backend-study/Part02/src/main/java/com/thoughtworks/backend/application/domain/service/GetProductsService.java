package com.thoughtworks.backend.application.domain.service;

import com.thoughtworks.backend.application.domain.model.product.Product;
import com.thoughtworks.backend.application.port.in.GetProductsUseCase;
import com.thoughtworks.backend.application.port.out.LoadProductsPort;
import com.thoughtworks.backend.common.tag.UseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetProductsService implements GetProductsUseCase {

    private final LoadProductsPort loadProductsPort;

    @Override
    public List<Product> getProducts() {
        return loadProductsPort.loadProducts();
    }

}
