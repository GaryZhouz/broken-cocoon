package com.thoughtworks.shopbackend.service.address;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thoughtworks.shopbackend.entity.ShippingAddressEntity;
import com.thoughtworks.shopbackend.dto.ShippingAddressDto;
import com.thoughtworks.shopbackend.mapper.ShippingAddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingAddressServiceImpl extends ServiceImpl<ShippingAddressMapper, ShippingAddressEntity> implements ShippingAddressService {

    private final ShippingAddressMapper shippingAddressMapper;

    @Override
    public Integer save(ShippingAddressDto shippingAddressDto) {
        ShippingAddressEntity shippingAddressEntity = shippingAddressDto.to();
        shippingAddressMapper.insert(shippingAddressEntity);
        return shippingAddressEntity.getId();
    }

}
