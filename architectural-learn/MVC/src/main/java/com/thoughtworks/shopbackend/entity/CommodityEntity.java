package com.thoughtworks.shopbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.thoughtworks.shopbackend.dto.CommodityDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@TableName("commodity")
public class CommodityEntity {

    @TableId(type = IdType.AUTO)
    private Integer sku;

    private String title;

    private String description;

    private BigDecimal amount;

    public CommodityDto to() {
        return CommodityDto.builder()
                .sku(sku)
                .title(title)
                .description(description)
                .amount(amount)
                .build();
    }
}
