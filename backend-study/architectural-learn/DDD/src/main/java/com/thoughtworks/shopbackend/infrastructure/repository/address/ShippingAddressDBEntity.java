package com.thoughtworks.shopbackend.infrastructure.repository.address;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("shipping_address")
public class ShippingAddressDBEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String address;

    private String fullName;

    private String phoneNumber;

    private Integer customerId;

}
