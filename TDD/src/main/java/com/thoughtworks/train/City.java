package com.thoughtworks.train;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, City> nameMapCity = new HashMap<>();
        input.forEach(params -> {
            String startCityName = params.substring(0, 1);
            String destinationCityName = params.substring(1, 2);
            Integer distance = Integer.parseInt(params.substring(2));

            City startCity = nameMapCity.getOrDefault(startCityName, City.builder().name(startCityName).build());
            City destinationCity = nameMapCity.getOrDefault(destinationCityName, City.builder().name(destinationCityName).build());
            startCity.getCanArriveCities().add(ArriveCity.from(destinationCity, distance));

            nameMapCity.putIfAbsent(startCityName, startCity);
            nameMapCity.putIfAbsent(destinationCityName, destinationCity);
        });
        return City.builder()
                .name("root")
                .canArriveCities(nameMapCity.values().stream()
                        .map(city -> ArriveCity.from(city, 0))
                        .toList())
                .build();
    }

}
