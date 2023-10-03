package com.thoughtworks.shopbackend.application.commodity.dto;

import com.thoughtworks.shopbackend.domain.commodity.Commodity;
import com.thoughtworks.shopbackend.domain.image.Image;
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

    private List<Image> images;

    public static CommodityDTO from(Commodity commodity) {
        CommodityDTO commodityDto = new CommodityDTO();
        BeanUtils.copyProperties(commodity, commodityDto);
        return commodityDto;
    }

    public Commodity to() {
        Commodity commodity = new Commodity();
        BeanUtils.copyProperties(this, commodity);
        return commodity;
    }
}
