package com.thoughtworks.shopbackend.domain.commodity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Commodity {

    private Integer sku;

    private String title;

    private String description;

    private BigDecimal amount;

    private List<Image> images;

}
