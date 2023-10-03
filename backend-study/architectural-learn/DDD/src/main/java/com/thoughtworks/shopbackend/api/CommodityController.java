package com.thoughtworks.shopbackend.api;

import com.thoughtworks.shopbackend.api.request.QueryRequest;
import com.thoughtworks.shopbackend.application.commodity.CommodityService;
import com.thoughtworks.shopbackend.application.commodity.dto.CommodityDTO;
import com.thoughtworks.shopbackend.infrastructure.model.JsonResult;
import com.thoughtworks.shopbackend.infrastructure.model.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.thoughtworks.shopbackend.api.Constant.COMMODITY_API;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(COMMODITY_API)
public class CommodityController {

    private final CommodityService commodityService;

    @GetMapping("page")
    public JsonResult<PageResult<CommodityDTO>> pageQuery(@Valid QueryRequest queryRequest) {
        return JsonResult.<PageResult<CommodityDTO>>builder()
                .data(commodityService.pageQuery(queryRequest.to()))
                .build();
    }

    @GetMapping("{sku}")
    public JsonResult<CommodityDTO> commodity(@PathVariable("sku") Integer sku) {
        return JsonResult.<CommodityDTO>builder()
                .data(commodityService.getCommodityBySku(sku))
                .build();
    }
}
