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

    public static ArriveCity from(City city, Integer distance) {
        return ArriveCity.builder()
                .city(city)
                .distance(distance)
                .build();
    }

}
