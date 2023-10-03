create table shop.image
(
    id             int auto_increment
        primary key,
    url            varchar(2083) not null,
    alternate_text varchar(128)  not null,
    sku            int           not null,
    constraint id
        unique (id),
    constraint sku
        unique (sku),
    constraint fk_commodity_image
        foreign key (sku) references shop.commodityDto (sku)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

