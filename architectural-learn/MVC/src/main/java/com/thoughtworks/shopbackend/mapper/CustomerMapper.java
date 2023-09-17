package com.thoughtworks.shopbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thoughtworks.shopbackend.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerMapper extends BaseMapper<CustomerEntity> {

}
