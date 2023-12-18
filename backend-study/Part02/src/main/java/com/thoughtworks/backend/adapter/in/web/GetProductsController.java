package com.thoughtworks.backend.adapter.in.web;

import com.thoughtworks.backend.application.domain.model.product.Product;
import com.thoughtworks.backend.application.port.in.GetProductsUseCase;
import com.thoughtworks.backend.common.model.JsonResult;
import com.thoughtworks.backend.common.tag.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class GetProductsController {

    private final GetProductsUseCase getProductsUseCase;

    @GetMapping("/getProducts")
    public JsonResult<List<Product>> getProducts() {
        return JsonResult.of(getProductsUseCase.getProducts());
    }

}
