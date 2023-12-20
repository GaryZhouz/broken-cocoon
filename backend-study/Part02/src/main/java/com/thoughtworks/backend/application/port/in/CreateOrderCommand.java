package com.thoughtworks.backend.application.port.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public record CreateOrderCommand(
        @Valid @NotEmpty(message = "selected products must no be empty!") List<CreateOrderProduct> products,
        @NotBlank String customerId,
        @NotBlank String receivingAddress,
        @Pattern(regexp = "^1[3456789]\\d{9}$") String receivingNumber
) {

    public String totalPrice() {
        if (CollectionUtils.isEmpty(this.products)) {
            return "0";
        }
        return String.valueOf(this.products()
                .stream()
                .mapToDouble(product -> Double.parseDouble(Optional.of(product.price()).orElse("0")))
                .sum());
    }

    public String totalDiscountPrice() {
        if (CollectionUtils.isEmpty(this.products)) {
            return "0";
        }
        return String.valueOf(this.products()
                .stream()
                .mapToDouble(product -> Double.parseDouble(Optional.of(product.discountPrice()).orElse(product.price())))
                .sum());
    }

    public record CreateOrderProduct(@NotBlank String id,
                                     @NotBlank String name,
                                     @Min(1) @NotNull Integer quantity,
                                     @NotBlank String price,
                                     @NotBlank String discountPrice) {

    }

}
