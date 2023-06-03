package com.thoughtworks.train;

import lombok.Data;

import java.util.List;

@Data
public class City {

    private String name;

    private List<ArriveCity> canArriveCities;

    private City() {
    }

}
