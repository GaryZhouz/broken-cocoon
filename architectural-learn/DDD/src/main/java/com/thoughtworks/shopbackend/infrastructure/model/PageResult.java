package com.thoughtworks.shopbackend.infrastructure.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageResult<T> {

    private List<T> data;

    private long total;
}
