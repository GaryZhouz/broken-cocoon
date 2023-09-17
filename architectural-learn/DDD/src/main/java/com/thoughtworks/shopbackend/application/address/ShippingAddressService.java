package com.thoughtworks.shopbackend.application.address;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thoughtworks.shopbackend.infrastructure.repository.address.ShippingAddressDBEntity;
import com.thoughtworks.shopbackend.infrastructure.repository.address.ShippingAddressDBEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingAddressService extends ServiceImpl<ShippingAddressDBEntityRepository, ShippingAddressDBEntity> {
}
