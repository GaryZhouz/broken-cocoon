package com.thoughtworks.shopbackend.infrastructure.repository.image;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.thoughtworks.shopbackend.domain.image.Image;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("image")
public class ImageDBEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String url;

    private String alternateText;

    private Integer sku;

    public Image to() {
        return Image.builder()
                .id(id)
                .url(url)
                .alternateText(alternateText)
                .sku(sku)
                .build();
    }
}
