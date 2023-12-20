alter table t_order
    add total_discount_price varchar(11) not null;

alter table t_order_product
    add product_discount_price varchar(11) not null;
