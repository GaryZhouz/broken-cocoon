create table shop.customer
(
    id    int auto_increment
        primary key,
    name varchar(255) not null,
    constraint customer_id
        unique (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

