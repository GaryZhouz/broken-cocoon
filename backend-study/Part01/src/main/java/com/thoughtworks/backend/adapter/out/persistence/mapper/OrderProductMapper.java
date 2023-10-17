package com.thoughtworks.backend.adapter.out.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thoughtworks.backend.adapter.out.persistence.entity.OrderProductEntity;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface OrderProductMapper extends BaseMapper<OrderProductEntity> {

    Integer insertBatchSomeColumn(Collection<OrderProductEntity> collection);

}
