package com.thoughtworks.shopbackend.infrastructure.repository.address;

import com.thoughtworks.shopbackend.domain.address.ShippingAddress;
import com.thoughtworks.shopbackend.domain.address.ShippingAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShippingAddressRepositoryImpl implements ShippingAddressRepository {

    private final ShippingAddressDBEntityRepository shippingAddressDBEntityRepository;

    @Override
    public Integer save(ShippingAddress shippingAddress) {
        ShippingAddressDBEntity shippingAddressDBEntity = shippingAddress.to();
        shippingAddressDBEntityRepository.insert(shippingAddressDBEntity);
        return shippingAddressDBEntity.getId();
    }

}
