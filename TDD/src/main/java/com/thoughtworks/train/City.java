package com.thoughtworks.train;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class City {

    private String name;

    @Builder.Default
    private List<ArriveCity> canArriveCities = new ArrayList<>();

    private City() {
    }

    public static City generate(List<String> input) {
        String params = input.get(0);
        String startCityName = params.substring(0, 1);
        String destinationCityName = params.substring(1, 2);
        Integer distance = Integer.parseInt(params.substring(2));
        City startCity = City.builder()
                .name(startCityName)
                .build();
        City destinationCity = City.builder()
                .name(destinationCityName)
                .build();
        List<ArriveCity> arriveCities = startCity.getCanArriveCities();
        arriveCities.add(ArriveCity.builder()
                .city(destinationCity)
                .distance(distance)
                .build());

        return startCity;
    }

}
