package com.thoughtworks.shopbackend.domain.image;

import java.util.List;

public interface ImageRepository {

    List<Image> getImagesBySku(Integer sku);

}
