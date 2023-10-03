package com.thoughtworks.shopbackend.domain.commodity;

import com.thoughtworks.shopbackend.domain.model.PageQuery;
import com.thoughtworks.shopbackend.infrastructure.model.PageResult;

public interface CommodityRepository {

    PageResult<Commodity> pageQuery(PageQuery query);

    Commodity getBySku(Integer sku);
}
