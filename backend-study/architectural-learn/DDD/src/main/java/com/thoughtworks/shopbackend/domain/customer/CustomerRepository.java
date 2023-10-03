package com.thoughtworks.shopbackend.domain.customer;

public interface CustomerRepository {
    boolean checkUserExistByUserId(Integer userId);
}
