package com.thoughtworks.backend.model;

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
    private String message = "success";

    private T data;

    public JsonResult(Integer code, String message) {
        this(code, message, null);
    }
}
