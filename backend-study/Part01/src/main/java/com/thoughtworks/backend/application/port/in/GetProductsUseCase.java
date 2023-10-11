package com.thoughtworks.backend.application.port.in;

import com.thoughtworks.backend.application.domain.model.product.Product;

import java.util.List;

public interface GetProductsUseCase {

    List<Product> getProducts();

}
