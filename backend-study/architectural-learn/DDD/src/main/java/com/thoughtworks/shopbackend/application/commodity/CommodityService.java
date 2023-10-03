package com.thoughtworks.shopbackend.application.commodity;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thoughtworks.shopbackend.infrastructure.repository.commodity.CommodityDBEntity;
import com.thoughtworks.shopbackend.infrastructure.repository.commodity.CommodityDBEntityRepository;
import com.thoughtworks.shopbackend.application.commodity.dto.CommodityDTO;
import com.thoughtworks.shopbackend.domain.commodity.Commodity;
import com.thoughtworks.shopbackend.domain.commodity.CommodityRepository;
import com.thoughtworks.shopbackend.domain.image.Image;
import com.thoughtworks.shopbackend.domain.image.ImageRepository;
import com.thoughtworks.shopbackend.domain.model.PageQuery;
import com.thoughtworks.shopbackend.infrastructure.model.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommodityService extends ServiceImpl<CommodityDBEntityRepository, CommodityDBEntity> {

    private final CommodityRepository commodityRepository;

    private final ImageRepository imageRepository;

    public PageResult<CommodityDTO> pageQuery(PageQuery query) {
        PageResult<Commodity> commodityPageResult = commodityRepository.pageQuery(query);
        return PageResult.<CommodityDTO>builder()
                .data(commodityPageResult.getData().stream()
                        .map(CommodityDTO::from)
                        .peek(commodityDto -> commodityDto.setImages(imageRepository.getImagesBySku(commodityDto.getSku())))
                        .collect(Collectors.toList()))
                .total(commodityPageResult.getTotal())
                .build();
    }

    public CommodityDTO getCommodityBySku(Integer sku) {
        Commodity commodity = commodityRepository.getBySku(sku);
        List<Image> images = imageRepository.getImagesBySku(sku);
        CommodityDTO commodityDto = new CommodityDTO();
        BeanUtils.copyProperties(commodity, commodityDto);
        commodityDto.setImages(images);
        return commodityDto;
    }
}
