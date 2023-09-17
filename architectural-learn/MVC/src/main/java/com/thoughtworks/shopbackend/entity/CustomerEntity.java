package com.thoughtworks.shopbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("customer")
public class CustomerEntity {

    @TableId(type = IdType.AUTO)
    private Integer customerId;

}
