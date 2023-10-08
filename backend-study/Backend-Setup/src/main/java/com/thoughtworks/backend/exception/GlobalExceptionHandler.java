package com.thoughtworks.backend.exception;

import com.thoughtworks.backend.model.RestErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // BindException（@Validated @Valid 仅对于表单提交有效，对于以json格式提交将会失效）
    @ResponseBody
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestErrorResponse handle(BindException ex) {
        String message = "field valid error ";
        if (!Objects.isNull(ex.getFieldError()) && ex.getFieldError().getDefaultMessage() != null) {
            message = ex.getFieldError().getDefaultMessage();
        }
        return new RestErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestErrorResponse handle(MethodArgumentNotValidException ex) {
        String field = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getField();
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        return new RestErrorResponse(HttpStatus.BAD_REQUEST.value(), field + message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public RestErrorResponse handle(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        StringBuilder message = new StringBuilder();
        for (ConstraintViolation<?> constraint : violations) {
            message.append(constraint.getMessage())
                    .append(";");
        }
        return new RestErrorResponse(HttpStatus.BAD_REQUEST.value(), message.toString());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse processException(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Exception ex) {
        if (ex instanceof BusinessException businessException) {
            LOGGER.info(ex.getMessage(), ex);
            ErrorCode errorCode = businessException.getErrorCode();
            int code = errorCode.getCode();
            String desc = errorCode.getDesc();
            return new RestErrorResponse(code, desc);
        }

        LOGGER.error("服务器出现异常：", ex);
        return new RestErrorResponse(CommonErrorCode.UNKNOWN.getCode(),
                ex.getMessage() != null ? ex.getMessage() : CommonErrorCode.UNKNOWN.getDesc());
    }
}

