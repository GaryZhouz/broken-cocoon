package com.thoughtworks.shopbackend.service.commodity.dto;

import com.thoughtworks.shopbackend.dto.CommodityDto;
import com.thoughtworks.shopbackend.dto.ImageDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommodityDTO {

    private Integer sku;

    private String title;

    private String description;

    private BigDecimal amount;

    private List<ImageDto> imageDtos;

    public static CommodityDTO from(CommodityDto commodity) {
        CommodityDTO commodityDto = new CommodityDTO();
        BeanUtils.copyProperties(commodity, commodityDto);
        return commodityDto;
    }

    public CommodityDto to() {
        CommodityDto commodityDto = new CommodityDto();
        BeanUtils.copyProperties(this, commodityDto);
        return commodityDto;
    }
}
