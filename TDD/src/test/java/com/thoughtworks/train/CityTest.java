package com.thoughtworks.train;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                assert model != null;
                List<ArriveCity> arriveCities = model.getCanArriveCities();
                City startCity = arriveCities.stream()
                        .filter(city -> startCityName.equals(city.getCity().getName()))
                        .findFirst()
                        .orElse(ArriveCity.builder().build())
                        .getCity();
                ArriveCity arriveCity = startCity.getCanArriveCities().get(0);

                // then
                assertEquals("root", model.getName());
                assertEquals(2, arriveCities.size());
                assertEquals(1, startCity.getCanArriveCities().size());
                assertEquals(startCityName, startCity.getName());
                assertEquals(destinationCityName, arriveCity.getCity().getName());
                assertEquals(distance, arriveCity.getDistance());
            }

            @Test
            void should_return_model_when_given_more_cities_input() {
                // given
                List<String> input = List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7");

                // when
                City model = City.generate(input);
                assert model != null;
                Map<String, City> nameMapCity = model.getCanArriveCities()
                        .stream()
                        .map(ArriveCity::getCity)
                        .collect(Collectors.toMap(City::getName, Function.identity()));

                // then
                Map<String, Map<String, Integer>> validMap = Map.of(
                        "A", Map.of("B", 5, "D", 5, "E", 7),
                        "B", Map.of("C", 4),
                        "C", Map.of("D", 8, "E", 2),
                        "D", Map.of("C", 8, "E", 6),
                        "E", Map.of("B", 3)
                );
                validMap.forEach((key, value) -> assertTrue(nameMapCity.get(key)
                        .getCanArriveCities()
                        .stream()
                        .allMatch(arriveCity -> {
                            String cityName = arriveCity.getCity().getName();
                            return value.containsKey(cityName) && Objects.equals(value.get(cityName), arriveCity.getDistance());
                        }))
                );
            }
        }

        @Nested
        class SadPath {
            @ParameterizedTest
            @ValueSource(strings = {"ABA", "BCC", "CDA", "DCD"})
            void should_return_null_when_given_distance_is_not_number(String args) {
                // given
                List<String> input = Collections.singletonList(args);

                // when
                City model = City.generate(input);

                // then
                assertNull(model);
            }

            @ParameterizedTest
            @ValueSource(strings = {"", "A", "A3", "B6"})
            void should_return_null_when_given_shorter_than_3_length_args(String args) {
                // given
                List<String> input = Collections.singletonList(args);

                // when
                City model = City.generate(input);

                // then
                assertNull(model);
            }
        }
    }

    @Nested
    class calculateDistance {
        @Nested
        class HappyPath {
            @ParameterizedTest
            @ValueSource(strings = {"AD", "BC", "CD", "DC", "EB"})
            void should_return_route_distance_when_given_two_length_route_can_arrive(String route) {
                // given
                City city = City.generate(List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

                // when
                assert city != null;
                String distance = city.calculateDistanceByRoute(route.toCharArray());

                // then
                Map<String, String> validMap = Map.of(
                        "AD", "5",
                        "BC", "4",
                        "CD", "8",
                        "DC", "8",
                        "EB", "3"
                );
                validMap.forEach((key, value) -> assertEquals(validMap.get(route), distance));
            }

            @ParameterizedTest
            @ValueSource(strings = {"ABC", "ADC", "AEBCD"})
            void should_return_route_distance_when_given_more_than_2_length_route_can_arrive(String route) {
                // given
                City city = City.generate(List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

                // when
                assert city != null;
                String distance = city.calculateDistanceByRoute(route.toCharArray());

                // then
                Map<String, String> validMap = Map.of(
                        "ABC", "9",
                        "ADC", "13",
                        "AEBCD", "22"
                );
                validMap.forEach((key, value) -> assertEquals(validMap.get(route), distance));
            }
        }

        @Nested
        class SadPath {
            @ParameterizedTest
            @ValueSource(strings = {"AC", "AED", "BDCD"})
            void should_return_no_such_route_when_given_inaccessible_route(String route) {
                // given
                City city = City.generate(List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

                // when
                assert city != null;
                String distance = city.calculateDistanceByRoute(route.toCharArray());

                // then
                assertEquals("NO SUCH ROUTE", distance);
            }
        }
    }
}
