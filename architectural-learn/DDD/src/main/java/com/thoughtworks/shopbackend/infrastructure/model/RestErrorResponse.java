package com.thoughtworks.shopbackend.infrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestErrorResponse {

    private Integer errCode;

    private String errMsg;

}
