alter table part2.t_order_product
    add constraint t_order_product_union_order___fk foreign key (order_id) references part2.t_order (id);
