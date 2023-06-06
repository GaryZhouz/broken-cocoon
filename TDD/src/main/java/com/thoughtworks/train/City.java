package com.thoughtworks.train;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

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
        boolean validInput = input.stream()
                .allMatch(param -> param.length() >= 3 && param.substring(2).matches("^[0-9]+$"));
        if (!validInput) {
            return null;
        }
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

    public String calculateDistanceByRoute(char... route) {
        String noSuchRoute = "NO SUCH ROUTE";
        City root = this;
        Integer distance = 0;
        for (char cityName : route) {
            Optional<ArriveCity> needArriveCity = root.getCanArriveCities()
                    .stream()
                    .filter(arriveCity -> arriveCity.getCity()
                            .getName()
                            .contains(String.valueOf(cityName))
                    ).findFirst();
            if (needArriveCity.isEmpty()) {
                return noSuchRoute;
            }
            distance += needArriveCity.get().getDistance();
            root = needArriveCity.get().getCity();
        }
        return String.valueOf(distance);
    }

    public List<String> calculateRoutes(String start, String arrival) {
        return this.calculateRoutes(start, arrival, true, (ignore) -> true);
    }

    public List<String> calculateRoutes(String start, String arrival, boolean ignoreCircle, Predicate<Provider> predicate) {
        List<String> routes = new ArrayList<>();
        List<City> path = new ArrayList<>();
        Optional<ArriveCity> startCity = this.getCanArriveCities().stream()
                .filter(arriveCity -> arriveCity
                        .getCity()
                        .getName()
                        .equals(start)
                ).findFirst();
        if (startCity.isEmpty()) {
            return Collections.emptyList();
        }
        dfsCity(startCity.get().getCity(), arrival, routes, path, ignoreCircle, predicate);
        return routes;
    }

    /**
     * 忽略环的情况下 计算所有的路线
     *
     * @param curCity          初始城市节点
     * @param arrival          需要到达城市节点名称
     * @param routes           所有的路线集合
     * @param path             记录当前路线的集合
     * @param ignoreCircle     是否忽略环
     * @param exitRecPredicate 退出的条件之一
     */
    public void dfsCity(
            City curCity,
            String arrival,
            List<String> routes,
            List<City> path,
            boolean ignoreCircle,
            Predicate<Provider> exitRecPredicate
    ) {
        path.add(curCity);
        if (curCity.getName().equals(arrival)) {
            routes.add(path.stream()
                    .map(City::getName)
                    .reduce("", (x, y) -> x + y));
        }
        List<ArriveCity> arriveCities = curCity.getCanArriveCities();
        for (ArriveCity canArriveCity : arriveCities) {
            City nextCity = canArriveCity.getCity();
            if (path.stream().noneMatch(arrivedCity -> arrivedCity.getName().equals(nextCity.getName()))) {
                dfsCity(nextCity, arrival, routes, path, ignoreCircle, exitRecPredicate);
                if (path.size() > 0) {
                    path.remove(path.size() - 1);
                }
            }
        }
    }

}
