package com.thoughtworks.shopbackend.application.order;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thoughtworks.shopbackend.infrastructure.exception.BusinessException;
import com.thoughtworks.shopbackend.infrastructure.exception.CommonErrorCode;
import com.thoughtworks.shopbackend.infrastructure.repository.order.OrderDBEntity;
import com.thoughtworks.shopbackend.infrastructure.repository.order.OrderDBEntityRepository;
import com.thoughtworks.shopbackend.application.commodity.dto.CommodityDTO;
import com.thoughtworks.shopbackend.application.order.dto.CreateOrderDto;
import com.thoughtworks.shopbackend.application.order.dto.OrderDTO;
import com.thoughtworks.shopbackend.domain.address.ShippingAddress;
import com.thoughtworks.shopbackend.domain.address.ShippingAddressRepository;
import com.thoughtworks.shopbackend.domain.commodity.Commodity;
import com.thoughtworks.shopbackend.domain.commodity.CommodityRepository;
import com.thoughtworks.shopbackend.domain.customer.CustomerRepository;
import com.thoughtworks.shopbackend.domain.image.Image;
import com.thoughtworks.shopbackend.domain.image.ImageRepository;
import com.thoughtworks.shopbackend.domain.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService extends ServiceImpl<OrderDBEntityRepository, OrderDBEntity> {

    private final OrderRepository orderRepository;

    private final CommodityRepository commodityRepository;

    private final ImageRepository imageRepository;

    private final ShippingAddressRepository shippingAddressRepository;

    private final CustomerRepository customerRepository;

    @Transactional
    public OrderDTO create(CreateOrderDto createOrderDto) {
        try {
            checkUserContainerNotPaidOrder(createOrderDto);
            OrderDTO orderDTO = buildOrderDTOByRequest(createOrderDto);
            Integer addressId = shippingAddressRepository.save(
                    ShippingAddress.builder()
                            .fullName(createOrderDto.getFullName())
                            .customerId(createOrderDto.getBuyerId())
                            .phoneNumber(createOrderDto.getPhoneNumber())
                            .address(createOrderDto.getAddress())
                            .build());
            orderDTO.setAddressId(addressId);
            Integer orderId = orderRepository.save(orderDTO.to());
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

    private OrderDTO buildOrderDTOByRequest(CreateOrderDto createOrderDto) {
        Commodity commodity = commodityRepository.getBySku(createOrderDto.getSku());
        List<Image> images = imageRepository.getImagesBySku(createOrderDto.getSku());
        OrderDTO orderDTO = createOrderDto.to();
        CommodityDTO commodityDTO = CommodityDTO.from(commodity);
        commodityDTO.setImages(images);
        orderDTO.setCommodity(commodityDTO);
        return orderDTO;
    }

    private void checkUserContainerNotPaidOrder(CreateOrderDto createOrderDto) {
        Integer userId = createOrderDto.getBuyerId();
        boolean checkUserExist = customerRepository.checkUserExistByUserId(userId);
        if (!checkUserExist) {
            throw new BusinessException(CommonErrorCode.USER_NOT_EXIST);
        }
        boolean containerNotPaidOrder = orderRepository.containerNotPaidOrder(userId);
        if (containerNotPaidOrder) {
            throw new BusinessException(CommonErrorCode.CONTAINER_NOT_PAID_ORDER);
        }
    }
}
