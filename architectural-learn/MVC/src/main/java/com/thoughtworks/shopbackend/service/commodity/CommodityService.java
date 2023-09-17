package com.thoughtworks.shopbackend.service.commodity;

import com.thoughtworks.shopbackend.components.model.PageQuery;
import com.thoughtworks.shopbackend.components.model.PageResult;
import com.thoughtworks.shopbackend.dto.CommodityDto;

public interface CommodityService {

    PageResult<CommodityDto> pageQuery(PageQuery query);

    CommodityDto getBySku(Integer sku);
}
