create table shop.shipping_address
(
    id           int auto_increment
        primary key,
    address      varchar(255) not null,
    full_name    varchar(32)  not null,
    phone_number varchar(11)  not null,
    customer_id  int          not null,
    constraint fk_owner_customer_id
        foreign key (customer_id) references shop.customer (customer_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

