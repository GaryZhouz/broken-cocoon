package com.thoughtworks.shopbackend.service.commodity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thoughtworks.shopbackend.entity.CommodityEntity;
import com.thoughtworks.shopbackend.components.exception.BusinessException;
import com.thoughtworks.shopbackend.components.exception.CommonErrorCode;
import com.thoughtworks.shopbackend.mapper.CommodityMapper;
import com.thoughtworks.shopbackend.dto.CommodityDto;
import com.thoughtworks.shopbackend.components.model.PageQuery;
import com.thoughtworks.shopbackend.components.model.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, CommodityEntity> implements CommodityService {

    private final CommodityMapper commodityMapper;

    @Override
    public CommodityDto getBySku(Integer sku) {
        return Optional.ofNullable(commodityMapper.selectById(sku))
                .orElseThrow(() -> new BusinessException(CommonErrorCode.COMMODITY_NOT_FOUND))
                .to();
    }

    @Override
    public PageResult<CommodityDto> pageQuery(PageQuery query) {
        IPage<CommodityEntity> commodityPage = commodityMapper.selectPage(
                new Page<>(query.getPage(), query.getSize()),
                new QueryWrapper<>()
        );
        return PageResult.<CommodityDto>builder()
                .data(commodityPage.getRecords()
                        .stream()
                        .map(CommodityEntity::to)
                        .collect(Collectors.toList()))
                .total(commodityPage.getTotal())
                .build();
    }
}
