package com.thoughtworks.shopbackend.domain.commodity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Commodity {

    private Integer sku;

    private String title;

    private String description;

    private BigDecimal amount;

}
