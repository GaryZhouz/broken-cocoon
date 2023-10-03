create table shop.commodityDto
(
    sku         int auto_increment
        primary key,
    title       varchar(255)   not null,
    description longtext       not null,
    amount      decimal(11, 2) not null,
    constraint sku
        unique (sku)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

