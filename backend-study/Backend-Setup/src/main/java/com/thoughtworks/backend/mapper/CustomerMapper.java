package com.thoughtworks.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thoughtworks.backend.entity.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerMapper extends BaseMapper<Customer> {
}
