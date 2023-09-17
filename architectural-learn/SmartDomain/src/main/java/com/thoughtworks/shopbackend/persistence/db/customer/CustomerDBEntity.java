package com.thoughtworks.shopbackend.persistence.db.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.thoughtworks.shopbackend.domain.customer.Customer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("customer")
public class CustomerDBEntity {

    @TableId(type = IdType.AUTO)
    private Integer customerId;

    public Customer to() {
        return Customer.builder()
                .customerId(this.getCustomerId())
                .build();
    }
}
