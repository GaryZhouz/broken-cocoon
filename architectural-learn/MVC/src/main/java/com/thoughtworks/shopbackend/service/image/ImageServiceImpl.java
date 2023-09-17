package com.thoughtworks.shopbackend.service.image;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thoughtworks.shopbackend.entity.ImageEntity;
import com.thoughtworks.shopbackend.dto.ImageDto;
import com.thoughtworks.shopbackend.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl extends ServiceImpl<ImageMapper, ImageEntity> implements ImageService {

    private final ImageMapper imageMapper;

    @Override
    public List<ImageDto> getImagesBySku(Integer sku) {
        return imageMapper.selectList(new QueryWrapper<ImageEntity>().eq("sku", sku))
                .stream()
                .map(ImageEntity::to)
                .collect(Collectors.toList());
    }
}
