package com.thoughtworks.shopbackend.persistence.db.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<OrderDBEntity> {

}
