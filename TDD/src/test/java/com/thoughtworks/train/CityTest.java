package com.thoughtworks.train;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
    class GenerateGraphModel {

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
    class Part01 {
        @Nested
        class CalculateDistance {
            @Nested
            class HappyPath {
                // Q1
                @ParameterizedTest
                @CsvSource({
                        "AD, 5",
                        "BC, 4",
                        "CD, 8",
                        "DC, 8",
                        "EB, 3"
                })
                void should_return_route_distance_when_given_two_length_route_can_arrive(String route, String result) {
                    // given
                    City city = City.generate(List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

                    // when
                    assert city != null;
                    String distance = city.calculateDistanceByRoute(route.toCharArray());

                    // then
                    assertEquals(result, distance);
                }

                // Q1 Q3 Q4
                @ParameterizedTest
                @CsvSource({
                        "ABC, 9",
                        "ADC, 13",
                        "AEBCD, 22"
                })
                void should_return_route_distance_when_given_more_than_2_length_route_can_arrive(String route, String result) {
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
                    assertEquals(result, distance);
                }
            }

            @Nested
            class SadPath {
                // Q5
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

        @Nested
        class CalculateRoutes {
            @Nested
            class IgnoreCircle {
                @Nested
                class HappyPath {
                    @ParameterizedTest
                    @ValueSource(strings = {"AC", "AE", "AD", "DB", "DE", "DC"})
                    void should_return_all_routes_when_given_start_arrive_station_can_be_arrived(String args) {
                        // given
                        String start = args.substring(0, 1);
                        String arrived = args.substring(1, 2);
                        City city = City.generate(List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

                        // when
                        assert city != null;
                        List<String> allRoutes = city.calculateRoutes(start, arrived);
                        System.out.println(args + " -> " + allRoutes);

                        // then
                        Map<String, List<String>> validMap = Map.of(
                                "AC", List.of("ABC", "ADC", "ADEBC", "AEBC"),
                                "AE", List.of("AE", "ABCE", "ABCDE", "ADE", "ADCE"),
                                "AD", List.of("AD", "ABCD", "AEBCD"),
                                "DB", List.of("DCEB", "DEB"),
                                "DE", List.of("DE", "DCE"),
                                "DC", List.of("DC", "DEBC")
                        );
                        assertTrue(allRoutes.containsAll(validMap.get(args)));
                    }
                }

                @Nested
                class SadPath {
                    @ParameterizedTest
                    @ValueSource(strings = {"BA", "DA", "EA", "ZZ"})
                    void should_return_all_routes_when_given_start_arrive_station_can_be_arrived(String args) {
                        // given
                        String start = args.substring(0, 1);
                        String arrived = args.substring(1, 2);
                        City city = City.generate(List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

                        // when
                        assert city != null;
                        List<String> allRoutes = city.calculateRoutes(start, arrived);
                        System.out.println(args + " -> " + allRoutes);

                        // then
                        assertTrue(allRoutes.isEmpty());
                    }
                }
            }

            @Nested
            class IncludeCircle {
                @Nested
                class HappyPath {
                    // Q10
                    @ParameterizedTest
                    @ValueSource(strings = {"AC", "CC"})
                    void should_return_all_routes_when_given_start_arrive_station_can_be_arrived_and_limit_max_distance(String args) {
                        // given
                        String start = args.substring(0, 1);
                        String arrived = args.substring(1, 2);
                        City city = City.generate(List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

                        // when
                        assert city != null;
                        List<String> allRoutes = city.calculateRoutes(start, arrived, false, provider -> provider.getDistance() < 30);
                        System.out.println(args + " -> " + allRoutes);

                        // then
                        Map<String, List<String>> validMap = Map.of(
                                "AC", List.of("ABC", "ABCDC", "ABCEBC", "ABCEBCEBC", "ADC", "ADCDC", "ADCEBC", "ADEBC", "ADEBCEBC", "AEBC", "AEBCEBC"),
                                "CC", List.of("CDC", "CDCEBC", "CDEBC", "CEBC", "CEBCDC", "CEBCEBC", "CEBCEBCEBC")
                        );
                        assertTrue(allRoutes.containsAll(validMap.get(args)));
                    }

                    // Q6
                    @Test
                    void should_return_all_routes_when_given_start_arrive_station_can_be_arrived_and_limit_max_steps() {
                        // given
                        String args = "CC";
                        String start = args.substring(0, 1);
                        String arrived = args.substring(1, 2);
                        City city = City.generate(List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

                        // when
                        assert city != null;
                        List<String> allRoutes = city.calculateRoutes(start, arrived, false, provider -> provider.getSteps() <= 3);
                        System.out.println(args + " -> " + allRoutes);

                        // then
                        Map<String, List<String>> validMap = Map.of(
                                "CC", List.of("CDC", "CEBC")
                        );
                        assertTrue(allRoutes.containsAll(validMap.get(args)));
                    }

                    // Q7
                    @ParameterizedTest
                    @ValueSource(strings = {"AC", "AD", "BB"})
                    void should_return_all_routes_when_given_start_arrive_station_can_be_arrived_and_equals_steps(String args) {
                        // given
                        String start = args.substring(0, 1);
                        String arrived = args.substring(1, 2);
                        City city = City.generate(List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

                        // when
                        assert city != null;
                        List<String> allRoutes = city.calculateRoutes(start, arrived, false, provider -> provider.getSteps() <= 4)
                                .stream()
                                .filter(route -> route.length() == 5)
                                .toList();
                        System.out.println(args + " -> " + allRoutes);

                        // then
                        Map<String, List<String>> validMap = Map.of(
                                "AC", List.of("ABCDC", "ADCDC", "ADEBC"),
                                "AD", List.of("AEBCD"),
                                "BB", List.of("BCDEB")
                        );
                        assertTrue(allRoutes.containsAll(validMap.get(args)));
                    }
                }

                @Nested
                class SadPath {
                    @ParameterizedTest
                    @ValueSource(strings = {"BA", "DA", "EA", "ZZ"})
                    void should_return_all_routes_when_given_start_arrive_station_can_not_be_arrived(String args) {
                        // given
                        String start = args.substring(0, 1);
                        String arrived = args.substring(1, 2);
                        City city = City.generate(List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

                        // when
                        assert city != null;
                        List<String> allRoutes = city.calculateRoutes(start, arrived, false, provider -> provider.getDistance() < 30);
                        System.out.println(args + " -> " + allRoutes);

                        // then
                        assertTrue(allRoutes.isEmpty());
                    }
                }
            }
        }

        @Nested
        class CalculateShortestRouteDistance {
            @Nested
            class HappyPath {
                // Q8-9
                @ParameterizedTest
                @CsvSource({
                        "AC, 9",
                        "BB, 9"
                })
                void should_return_shortest_distance_when_given_start_end_city_can_be_arrived(String args, String result) {
                    // given
                    String start = args.substring(0, 1);
                    String arrived = args.substring(1, 2);
                    City city = City.generate(List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

                    // when
                    assert city != null;
                    String shortestDistance = city.calculateShortestRoute(start, arrived, 30);

                    // then
                    assertEquals(result, shortestDistance);
                }
            }

            @Nested
            class SadPath {
                @ParameterizedTest
                @ValueSource(strings = {"AA", "ZZ"})
                void should_return_no_such_route_when_given_start_end_city_can_not_be_arrived(String args) {
                    // given
                    String start = args.substring(0, 1);
                    String arrived = args.substring(1, 2);
                    City city = City.generate(List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

                    // when
                    assert city != null;
                    String shortestDistance = city.calculateShortestRoute(start, arrived, 30);

                    // then
                    assertEquals("NO SUCH ROUTE", shortestDistance);
                }
            }
        }
    }

    @Nested
    class Part02 {
        @Nested
        class CalculateDuration {
            @Nested
            class HappyPath {
                // Q1-Q4
                @ParameterizedTest
                @CsvSource({
                        "ABC, 11",
                        "AD, 5",
                        "ADC, 15",
                        "AEBCD, 28"
                })
                void should_return_route_duration_when_given_route_can_arrive(String route, String result) {
                    // given
                    City city = City.generate(List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

                    // when
                    assert city != null;
                    String distance = city.calculateDurationByRoute(route.toCharArray());

                    // then
                    assertEquals(result, distance);
                }
            }

            @Nested
            class SadPath {
                // Q5
                @ParameterizedTest
                @ValueSource(strings = {"AC", "AED", "BDCD"})
                void should_return_route_duration_when_given_route_can_arrive(String route) {
                    // given
                    City city = City.generate(List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

                    // when
                    assert city != null;
                    String distance = city.calculateDurationByRoute(route.toCharArray());

                    // then
                    assertEquals("NO SUCH ROUTE", distance);
                }
            }
        }

        @Nested
        class CalculateRoutes {
            @Nested
            class HappyPath {
                // Q6
                @Test
                void should_return_all_routes_when_duration_shortest_limited_30() {
                    // given
                    String args = "CC";
                    String start = args.substring(0, 1);
                    String arrived = args.substring(1, 2);
                    City city = City.generate(List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

                    // when
                    assert city != null;
                    List<String> allRoutes = city.calculateRoutes(start, arrived, false, provider -> provider.getDuration() <= 30);
                    System.out.println(args + " -> " + allRoutes);

                    // then
                    List<String> expectRoutes = List.of("CDC", "CEBC", "CDEBC", "CEBCEBC");
                    assertTrue(allRoutes.containsAll(expectRoutes));
                }

                // Q10
                @Test
                void should_return_all_routes_when_duration_shortest_limited_35() {
                    // given
                    String args = "CC";
                    String start = args.substring(0, 1);
                    String arrived = args.substring(1, 2);
                    City city = City.generate(List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

                    // when
                    assert city != null;
                    List<String> allRoutes = city.calculateRoutes(start, arrived, false, provider -> provider.getDuration() < 35);
                    System.out.println(args + " -> " + allRoutes);

                    // then
                    List<String> expectRoutes = List.of("CDC", "CEBC", "CEBCDC", "CDCEBC", "CDEBC", "CEBCEBC");
                    assertTrue(allRoutes.containsAll(expectRoutes));
                }

                @Test
                void should_return_all_routes_when_duration_shortest_exactly_30() {
                    // given
                    String args = "AC";
                    String start = args.substring(0, 1);
                    String arrived = args.substring(1, 2);
                    City city = City.generate(List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

                    // when
                    assert city != null;
                    List<String> allRoutes = city.calculateRoutes(start, arrived, false, provider -> provider.getDuration() <= 30);
                    System.out.println(args + " -> " + allRoutes);

                    // then
                    List<String> expectRoutes = List.of("ADCEBC");
                    assertTrue(allRoutes.containsAll(expectRoutes));
                }
            }
        }

        @Nested
        class CalculateShortestRouteDuration {
            @Nested
            class HappyPath {
                // Q7-Q8
                @ParameterizedTest
                @CsvSource({
                        "AC, 11",
                        "BB, 13"
                })
                void should_return_the_shortest_route_when_given_start_end_city(String args, String result) {
                    // given
                    String start = args.substring(0, 1);
                    String arrived = args.substring(1, 2);
                    City city = City.generate(List.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

                    // when
                    assert city != null;
                    String minDuration = city.calculateShortest(start, arrived, 100);

                    // then
                    assertEquals(result, minDuration);
                }
            }
        }
    }
}
