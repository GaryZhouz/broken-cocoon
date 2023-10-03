package com.thoughtworks.shopbackend.domain.commodity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Image {

    private String url;

    private String alternateText;

}
