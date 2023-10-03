package com.thoughtworks.shopbackend.controller.request;

import com.thoughtworks.shopbackend.components.model.PageQuery;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Min;

@Data
public class QueryRequest {

    @Min(value = 1, message = "page no must gt 1")
    private Integer page = 1;

    @Min(value = 1, message = "page size must gt 1")
    private Integer size = 10;

    public PageQuery to() {
        PageQuery pageQuery = new PageQuery();
        BeanUtils.copyProperties(this, pageQuery);
        return pageQuery;
    }

}
