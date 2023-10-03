package com.thoughtworks.shopbackend.application.image;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thoughtworks.shopbackend.infrastructure.repository.image.ImageDBEntity;
import com.thoughtworks.shopbackend.infrastructure.repository.image.ImageDBEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService extends ServiceImpl<ImageDBEntityRepository, ImageDBEntity> {
}
