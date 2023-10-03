package com.thoughtworks.shopbackend.persistence.db.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.thoughtworks.shopbackend.domain.commodity.Commodity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@TableName("commodity")
public class CommodityDBEntity {

    @TableId(type = IdType.AUTO)
    private Integer sku;

    private String title;

    private String description;

    private BigDecimal amount;

    public Commodity to() {
        return Commodity.builder()
                .sku(sku)
                .title(title)
                .description(description)
                .amount(amount)
                .build();
    }
}
