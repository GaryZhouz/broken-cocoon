package com.thoughtworks.train;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ArriveCity {

    private Integer distance;

    private City city;

    private ArriveCity() {
    }

}
