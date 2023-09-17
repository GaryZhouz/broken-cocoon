package com.thoughtworks.shopbackend.api;

import com.thoughtworks.shopbackend.api.components.model.JsonResult;
import com.thoughtworks.shopbackend.domain.commodity.Commodity;
import com.thoughtworks.shopbackend.persistence.db.commodity.CommodityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.thoughtworks.shopbackend.api.Constant.COMMODITY_API;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(COMMODITY_API)
public class CommodityController {

    private final CommodityMapper commodityMapper;

    @GetMapping("{sku}")
    public JsonResult<Commodity> commodity(@PathVariable("sku") Integer sku) {
        return JsonResult.<Commodity>builder()
                .data(commodityMapper.selectById(sku).to())
                .build();
    }
}
