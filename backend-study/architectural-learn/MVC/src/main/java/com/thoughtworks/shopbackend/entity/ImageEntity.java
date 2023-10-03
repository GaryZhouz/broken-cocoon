package com.thoughtworks.shopbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.thoughtworks.shopbackend.dto.ImageDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("image")
public class ImageEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String url;

    private String alternateText;

    private Integer sku;

    public ImageDto to() {
        return ImageDto.builder()
                .id(id)
                .url(url)
                .alternateText(alternateText)
                .sku(sku)
                .build();
    }
}
