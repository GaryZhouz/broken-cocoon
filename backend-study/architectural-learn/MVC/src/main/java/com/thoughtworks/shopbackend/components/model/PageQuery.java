package com.thoughtworks.shopbackend.components.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageQuery {

    private Integer page;

    private Integer size;

}
