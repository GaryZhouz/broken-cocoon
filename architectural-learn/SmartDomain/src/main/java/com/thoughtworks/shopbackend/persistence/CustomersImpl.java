package com.thoughtworks.shopbackend.persistence;

import com.thoughtworks.shopbackend.domain.customer.Customer;
import com.thoughtworks.shopbackend.domain.customer.Customers;
import com.thoughtworks.shopbackend.persistence.db.address.ShoppingAddressMapper;
import com.thoughtworks.shopbackend.persistence.db.commodity.CommodityMapper;
import com.thoughtworks.shopbackend.persistence.db.customer.CustomerDBEntity;
import com.thoughtworks.shopbackend.persistence.db.customer.CustomerMapper;
import com.thoughtworks.shopbackend.persistence.db.order.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomersImpl implements Customers {

    private final CustomerMapper customerMapper;

    private final OrderMapper orderMapper;

    private final CommodityMapper commodityMapper;

    private final ShoppingAddressMapper shoppingAddressMapper;

    @Override
    public List<Customer> getCustomers() {
        return customerMapper.selectList(null).stream()
                .map(CustomerDBEntity::to)
                .peek(customer -> {
                    customer.setOrders(new CustomerOrdersImpl(customer.getCustomerId(), orderMapper, commodityMapper));
                    customer.setCustomerShoppingAddress(new CustomerShoppingAddressesImpl(customer.getCustomerId(), shoppingAddressMapper));
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> getCustomer(int customerId) {
        return Optional.of(customerMapper.selectById(customerId))
                .map(customerDBEntity -> {
                    Customer customer = customerDBEntity.to();
                    customer.setCustomerShoppingAddress(new CustomerShoppingAddressesImpl(customerId, shoppingAddressMapper));
                    customer.setOrders(new CustomerOrdersImpl(customerId, orderMapper, commodityMapper));
                    return customer;
                });
    }

}
