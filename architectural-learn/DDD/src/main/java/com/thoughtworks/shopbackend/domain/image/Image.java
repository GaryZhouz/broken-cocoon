package com.thoughtworks.shopbackend.domain.image;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Image {

    private Integer id;

    private String url;

    private String alternateText;

    private Integer sku;
}
