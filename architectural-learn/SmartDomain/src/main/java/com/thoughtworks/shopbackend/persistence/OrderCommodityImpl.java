package com.thoughtworks.shopbackend.persistence;

import com.thoughtworks.shopbackend.domain.commodity.Commodity;
import com.thoughtworks.shopbackend.domain.order.OrderCommodity;
import com.thoughtworks.shopbackend.persistence.db.commodity.CommodityMapper;
import com.thoughtworks.shopbackend.persistence.db.order.OrderMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderCommodityImpl implements OrderCommodity {

    private final Integer orderId;

    private final OrderMapper orderMapper;

    private final CommodityMapper commodityMapper;

    @Override
    public Commodity getOrderCommodity() {
        return commodityMapper.selectById(orderMapper.selectById(orderId).getSku())
                .to();
    }

}
