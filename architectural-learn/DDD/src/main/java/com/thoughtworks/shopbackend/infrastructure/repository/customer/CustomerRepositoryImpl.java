package com.thoughtworks.shopbackend.infrastructure.repository.customer;

import com.thoughtworks.shopbackend.domain.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerDBEntityRepository customerDBEntityRepository;

    @Override
    public boolean checkUserExistByUserId(Integer userId) {
        return customerDBEntityRepository.selectById(userId) != null;
    }
}
