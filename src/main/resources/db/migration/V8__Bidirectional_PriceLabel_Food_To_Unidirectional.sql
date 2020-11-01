DROP TABLE IF EXISTS food_price_label;

ALTER TABLE price_label
    ADD COLUMN food_id bigint(20) NOT NULL;

ALTER TABLE price_label
    ADD FOREIGN KEY (food_id) references food (id);