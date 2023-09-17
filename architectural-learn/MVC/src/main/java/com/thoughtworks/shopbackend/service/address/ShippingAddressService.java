package com.thoughtworks.shopbackend.service.address;

import com.thoughtworks.shopbackend.dto.ShippingAddressDto;

public interface ShippingAddressService {

    Integer save(ShippingAddressDto shippingAddressDto);

}
