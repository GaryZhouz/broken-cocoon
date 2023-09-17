create table shop.`order`
(
    order_id     int auto_increment
        primary key,
    quantity     int      not null,
    total_price  double   not null,
    order_status int      null,
    sku          int      not null,
    buyer_id     int      not null,
    create_at    datetime not null,
    address_id   int      not null,
    constraint fk_buyer_id
        foreign key (buyer_id) references shop.customer (customer_id),
    constraint fk_order_address_id
        foreign key (address_id) references shop.shipping_address (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

