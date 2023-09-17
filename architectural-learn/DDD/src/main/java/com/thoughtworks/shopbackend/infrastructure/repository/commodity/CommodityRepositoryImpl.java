package com.thoughtworks.shopbackend.infrastructure.repository.commodity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thoughtworks.shopbackend.domain.commodity.Commodity;
import com.thoughtworks.shopbackend.domain.commodity.CommodityRepository;
import com.thoughtworks.shopbackend.domain.model.PageQuery;
import com.thoughtworks.shopbackend.infrastructure.exception.BusinessException;
import com.thoughtworks.shopbackend.infrastructure.exception.CommonErrorCode;
import com.thoughtworks.shopbackend.infrastructure.model.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommodityRepositoryImpl implements CommodityRepository {

    private final CommodityDBEntityRepository commodityDBEntityRepository;

    @Override
    public Commodity getBySku(Integer sku) {
        return Optional.ofNullable(commodityDBEntityRepository.selectById(sku))
                .orElseThrow(() -> new BusinessException(CommonErrorCode.COMMODITY_NOT_FOUND))
                .to();
    }

    @Override
    public PageResult<Commodity> pageQuery(PageQuery query) {
        IPage<CommodityDBEntity> commodityPage = commodityDBEntityRepository.selectPage(
                new Page<>(query.getPage(), query.getSize()),
                new QueryWrapper<>()
        );
        return PageResult.<Commodity>builder()
                .data(commodityPage.getRecords()
                        .stream()
                        .map(CommodityDBEntity::to)
                        .collect(Collectors.toList()))
                .total(commodityPage.getTotal())
                .build();
    }
}
