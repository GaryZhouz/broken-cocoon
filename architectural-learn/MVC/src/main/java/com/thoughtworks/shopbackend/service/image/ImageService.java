package com.thoughtworks.shopbackend.service.image;

import com.thoughtworks.shopbackend.dto.ImageDto;

import java.util.List;

public interface ImageService {

    List<ImageDto> getImagesBySku(Integer sku);

}
