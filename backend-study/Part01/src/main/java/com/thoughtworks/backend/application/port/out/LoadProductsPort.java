package com.thoughtworks.backend.application.port.out;

import com.thoughtworks.backend.application.domain.model.product.Product;

import java.util.List;

public interface LoadProductsPort {

    List<Product> loadProducts();

}

