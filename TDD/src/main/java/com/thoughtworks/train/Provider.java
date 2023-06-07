package com.thoughtworks.train;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Provider {

    private Integer distance;

    private Integer steps;

}
