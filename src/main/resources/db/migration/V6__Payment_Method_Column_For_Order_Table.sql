ALTER TABLE orders
    DROP FOREIGN KEY orders_ibfk_1;

ALTER TABLE orders
    ADD COLUMN payment_method varchar(255);

ALTER TABLE orders
    ADD FOREIGN KEY (user_id) references usr (id);