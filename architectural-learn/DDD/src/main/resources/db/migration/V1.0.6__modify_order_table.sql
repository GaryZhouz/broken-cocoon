rename
table shop.`order` to t_order;

alter table t_order
    modify order_status varchar(32) null;
