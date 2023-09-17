package com.thoughtworks.shopbackend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDto {

    private Integer id;

    private String url;

    private String alternateText;

    private Integer sku;
}
