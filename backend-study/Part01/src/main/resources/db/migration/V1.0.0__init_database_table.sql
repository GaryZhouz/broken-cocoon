create table t_product
(
    id     varchar(11)                   not null
        primary key,
    name   varchar(255)                  not null,
    price  varchar(11) null,
    status varchar(32) default 'INVALID' not null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

create table t_order
(
    id               int auto_increment
        primary key,
    total_price      varchar(11)                   not null,
    status           varchar(32) default 'CREATED' not null,
    create_time      datetime                      not null,
    create_by        int                           not null,
    receivingAddress varchar(255)                  not null,
    receivingNumber  varchar(11)                   not null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

create table t_order_product
(
    id            int auto_increment
        primary key,
    order_id      int         not null,
    product_id    varchar(11) not null,
    product_name  varchar(255) null,
    product_price varchar(11) null,
    quantity      int         not null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
