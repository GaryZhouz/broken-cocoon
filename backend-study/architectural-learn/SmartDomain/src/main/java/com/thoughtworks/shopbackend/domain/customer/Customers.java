package com.thoughtworks.shopbackend.domain.customer;

import java.util.List;
import java.util.Optional;

public interface Customers {

    List<Customer> getCustomers();

    Optional<Customer> getCustomer(int customerId);

}
