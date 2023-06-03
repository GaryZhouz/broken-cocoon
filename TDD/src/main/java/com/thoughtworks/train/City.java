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
        return null;
    }

}
