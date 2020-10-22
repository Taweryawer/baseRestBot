CREATE TABLE price_category
(
    id    bigint NOT NULL AUTO_INCREMENT,
    title varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE price_label
(
    id                bigint NOT NULL AUTO_INCREMENT,
    value             double NOT NULL,
    price_category_id bigint NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (price_category_id) references price_category (id)
);

CREATE TABLE food_price_label
(
    food_id        bigint NOT NULL,
    price_label_id bigint NOT NULL,
    FOREIGN KEY (food_id) references food (id),
    FOREIGN KEY (price_label_id) references price_label (id),
    UNIQUE (price_label_id)
)