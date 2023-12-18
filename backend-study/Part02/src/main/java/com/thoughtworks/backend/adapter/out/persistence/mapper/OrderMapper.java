package com.thoughtworks.backend.adapter.out.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thoughtworks.backend.adapter.out.persistence.entity.OrderEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<OrderEntity> {
}
