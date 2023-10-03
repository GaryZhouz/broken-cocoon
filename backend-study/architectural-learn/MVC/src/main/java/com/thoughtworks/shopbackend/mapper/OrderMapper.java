package com.thoughtworks.shopbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thoughtworks.shopbackend.entity.OrderEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<OrderEntity> {

}
