package com.thoughtworks.shopbackend.components.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult<T> {

    @Builder.Default
    private Integer code = 200;

    @Builder.Default
    private String message = "成功";

    private T data;

    public JsonResult(Integer code, String message) {
        this(code, message, null);
    }
}