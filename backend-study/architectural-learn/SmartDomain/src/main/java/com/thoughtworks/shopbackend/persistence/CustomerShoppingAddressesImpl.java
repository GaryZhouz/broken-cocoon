package com.thoughtworks.shopbackend.persistence;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thoughtworks.shopbackend.domain.customer.CustomerShoppingAddress;
import com.thoughtworks.shopbackend.domain.customer.ShoppingAddress;
import com.thoughtworks.shopbackend.persistence.db.address.ShoppingAddressDBEntity;
import com.thoughtworks.shopbackend.persistence.db.address.ShoppingAddressMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomerShoppingAddressesImpl implements CustomerShoppingAddress {

    private final int customerId;

    private final ShoppingAddressMapper shoppingAddressMapper;

    @Override
    public List<ShoppingAddress> getCustomerShoppingAddresses() {
        return shoppingAddressMapper.selectList(new QueryWrapper<ShoppingAddressDBEntity>().eq("customer_id", customerId))
                .stream()
                .map(ShoppingAddressDBEntity::to)
                .collect(Collectors.toList());
    }

    @Override
    public ShoppingAddress addShoppingAddress(ShoppingAddress shoppingAddress) {
        shoppingAddressMapper.insert(ShoppingAddressDBEntity.form(shoppingAddress));
        return shoppingAddress;
    }
}
