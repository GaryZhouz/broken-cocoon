package com.thoughtworks.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thoughtworks.backend.entity.Customer;
import com.thoughtworks.backend.exception.BusinessException;
import com.thoughtworks.backend.exception.CommonErrorCode;
import com.thoughtworks.backend.mapper.CustomerMapper;
import com.thoughtworks.backend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    private final CustomerMapper customerMapper;

    @Override
    public Customer getById(Integer id) {
        return Optional.ofNullable(customerMapper.selectById(id))
                .orElseThrow(() -> new BusinessException(CommonErrorCode.NOT_FOUND));
    }

}
