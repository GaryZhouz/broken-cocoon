package com.thoughtworks.shopbackend.service.customer;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thoughtworks.shopbackend.entity.CustomerEntity;
import com.thoughtworks.shopbackend.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, CustomerEntity> implements CustomerService {

    private final CustomerMapper customerMapper;

    @Override
    public boolean checkUserExistByUserId(Integer userId) {
        return customerMapper.selectById(userId) != null;
    }

}
