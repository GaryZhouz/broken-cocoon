package com.thoughtworks.train;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CityTest {

    @Nested
    class generateGraphModel {

        @Nested
        class HappyPath {
            @ParameterizedTest
            @ValueSource(strings = {"AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"})
            void should_return_model_when_given_two_cities_input(String args) {
                // given
                List<String> input = Collections.singletonList(args);
                String startCityName = args.substring(0, 1);
                String destinationCityName = args.substring(1, 2);
                Integer distance = Integer.parseInt(args.substring(2));

                // when
                City model = City.generate(input);

                // then
                assertEquals(startCityName, model.getName());
                assertEquals(1, model.getCanArriveCities().size());
                ArriveCity arriveCity = model.getCanArriveCities().get(0);
                assertEquals(destinationCityName, arriveCity.getCity().getName());
                assertEquals(distance, arriveCity.getDistance());
            }

        }

    }

}
