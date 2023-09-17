package com.thoughtworks.shopbackend.domain.customer;

import java.util.List;

public interface CustomerShoppingAddress {

    List<ShoppingAddress> getCustomerShoppingAddresses();

    ShoppingAddress addShoppingAddress(ShoppingAddress shoppingAddress);
}
