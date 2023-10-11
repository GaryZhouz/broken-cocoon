package com.thoughtworks.backend.common.model;

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

    private T data;

    @Builder.Default
    private String message = "Success";

    public static <T> JsonResult<T> of(T data) {
        return JsonResult.<T>builder()
                .data(data)
                .build();
    }

    public static <T> JsonResult<T> of(Integer code, T data) {
        return JsonResult.<T>builder()
                .code(code)
                .data(data)
                .build();
    }

    public static <T> JsonResult<T> of(Integer code, String message, T data) {
        return JsonResult.<T>builder()
                .code(code)
                .data(data)
                .message(message)
                .build();
    }

    public JsonResult(Integer code, String message) {
        this(code, null, message);
    }

}
