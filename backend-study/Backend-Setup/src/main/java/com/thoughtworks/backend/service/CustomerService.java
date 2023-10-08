package com.thoughtworks.backend.service;

import com.thoughtworks.backend.entity.Customer;

public interface CustomerService {

    Customer getById(Integer id);

}
