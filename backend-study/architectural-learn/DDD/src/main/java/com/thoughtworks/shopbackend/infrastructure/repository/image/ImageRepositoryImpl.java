package com.thoughtworks.shopbackend.infrastructure.repository.image;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thoughtworks.shopbackend.domain.image.Image;
import com.thoughtworks.shopbackend.domain.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {

    private final ImageDBEntityRepository imageDBEntityRepository;

    @Override
    public List<Image> getImagesBySku(Integer sku) {
        return imageDBEntityRepository.selectList(new QueryWrapper<ImageDBEntity>().eq("sku", sku))
                .stream()
                .map(ImageDBEntity::to)
                .collect(Collectors.toList());
    }
}
