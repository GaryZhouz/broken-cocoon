package com.thoughtworks.shopbackend.service.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thoughtworks.shopbackend.entity.OrderEntity;
import com.thoughtworks.shopbackend.components.model.OrderStatus;
import com.thoughtworks.shopbackend.dto.OrderDto;
import com.thoughtworks.shopbackend.mapper.OrderMapper;
import com.thoughtworks.shopbackend.controller.request.CreateOrderRequest;
import com.thoughtworks.shopbackend.dto.CommodityDto;
import com.thoughtworks.shopbackend.service.commodity.CommodityService;
import com.thoughtworks.shopbackend.service.commodity.dto.CommodityDTO;
import com.thoughtworks.shopbackend.service.order.dto.OrderDTO;
import com.thoughtworks.shopbackend.dto.ShippingAddressDto;
import com.thoughtworks.shopbackend.service.address.ShippingAddressService;
import com.thoughtworks.shopbackend.service.customer.CustomerService;
import com.thoughtworks.shopbackend.dto.ImageDto;
import com.thoughtworks.shopbackend.service.image.ImageService;
import com.thoughtworks.shopbackend.components.exception.BusinessException;
import com.thoughtworks.shopbackend.components.exception.CommonErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderService {

    private final OrderMapper orderMapper;

    private final CommodityService commodityService;

    private final ImageService imageService;

    private final ShippingAddressService shippingAddressService;

    private final CustomerService customerService;

    @Transactional
    public OrderDTO create(CreateOrderRequest orderRequest) {
        try {
            checkUserContainerNotPaidOrder(orderRequest);
            OrderDTO orderDTO = buildOrderDTOByRequest(orderRequest);
            Integer addressId = shippingAddressService.save(
                    ShippingAddressDto.builder()
                            .fullName(orderRequest.getFullName())
                            .customerId(orderRequest.getBuyerId())
                            .phoneNumber(orderRequest.getPhoneNumber())
                            .address(orderRequest.getAddress())
                            .build());
            orderDTO.setAddressId(addressId);
            Integer orderId = this.save(orderDTO.to());
            orderDTO.setOrderId(orderId);
            return orderDTO;
        } catch (Exception e) {
            log.error(e.getMessage());
            if (e instanceof BusinessException) {
                throw e;
            }
            throw new BusinessException(CommonErrorCode.CREATE_ORDER_FAILED);
        }
    }

    private OrderDTO buildOrderDTOByRequest(CreateOrderRequest orderRequest) {
        CommodityDto commodityDto = commodityService.getBySku(orderRequest.getSku());
        List<ImageDto> imageDtos = imageService.getImagesBySku(orderRequest.getSku());
        OrderDTO orderDTO = orderRequest.to();
        CommodityDTO commodityDTO = CommodityDTO.from(commodityDto);
        commodityDTO.setImageDtos(imageDtos);
        orderDTO.setCommodity(commodityDTO);
        return orderDTO;
    }

    private void checkUserContainerNotPaidOrder(CreateOrderRequest orderRequest) {
        Integer userId = orderRequest.getBuyerId();
        boolean checkUserExist = customerService.checkUserExistByUserId(userId);
        if (!checkUserExist) {
            throw new BusinessException(CommonErrorCode.USER_NOT_EXIST);
        }
        boolean containerNotPaidOrder = this.containerNotPaidOrder(userId);
        if (containerNotPaidOrder) {
            throw new BusinessException(CommonErrorCode.CONTAINER_NOT_PAID_ORDER);
        }
    }

    @Override
    public Integer save(OrderDto orderDto) {
        OrderEntity orderEntity = orderDto.to();
        orderMapper.insert(orderEntity);
        return orderEntity.getOrderId();
    }

    @Override
    public boolean containerNotPaidOrder(Integer userId) {
        QueryWrapper<OrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("buyer_id", userId)
                .eq("order_status", OrderStatus.NOT_PAID);
        return orderMapper.selectCount(queryWrapper) > 0;
    }
}
