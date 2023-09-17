create table shop.customer
(
    customer_id int auto_increment
        primary key,
    constraint customer_id
        unique (customer_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

