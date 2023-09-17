package com.thoughtworks.shopbackend.persistence.db.address;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.thoughtworks.shopbackend.domain.customer.ShoppingAddress;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("commodity")
public class ShoppingAddressDBEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String address;

    private String fullName;

    private String phoneNumber;

    private Integer customerId;

    public static ShoppingAddressDBEntity form(ShoppingAddress shoppingAddress) {
        return ShoppingAddressDBEntity.builder()
                .address(shoppingAddress.getAddress())
                .fullName(shoppingAddress.getFullName())
                .phoneNumber(shoppingAddress.getPhoneNumber())
                .build();
    }

    public ShoppingAddress to() {
        return ShoppingAddress.builder()
                .address(address)
                .fullName(fullName)
                .phoneNumber(phoneNumber)
                .build();
    }
}
