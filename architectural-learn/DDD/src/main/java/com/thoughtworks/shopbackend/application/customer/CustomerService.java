package com.thoughtworks.shopbackend.application.customer;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thoughtworks.shopbackend.infrastructure.repository.customer.CustomerDBEntity;
import com.thoughtworks.shopbackend.infrastructure.repository.customer.CustomerDBEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService extends ServiceImpl<CustomerDBEntityRepository, CustomerDBEntity> {
}
