package com.thoughtworks.shopbackend.controller;

import com.thoughtworks.shopbackend.components.model.JsonResult;
import com.thoughtworks.shopbackend.components.model.PageResult;
import com.thoughtworks.shopbackend.controller.request.QueryRequest;
import com.thoughtworks.shopbackend.dto.CommodityDto;
import com.thoughtworks.shopbackend.service.commodity.CommodityServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.thoughtworks.shopbackend.controller.Constant.COMMODITY_API;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(COMMODITY_API)
public class CommodityController {

    private final CommodityServiceImpl commodityServiceImpl;

    @GetMapping("page")
    public JsonResult<PageResult<CommodityDto>> pageQuery(@Valid QueryRequest queryRequest) {
        return JsonResult.<PageResult<CommodityDto>>builder()
                .data(commodityServiceImpl.pageQuery(queryRequest.to()))
                .build();
    }

    @GetMapping("{sku}")
    public JsonResult<CommodityDto> commodity(@PathVariable("sku") Integer sku) {
        return JsonResult.<CommodityDto>builder()
                .data(commodityServiceImpl.getBySku(sku))
                .build();
    }
}
