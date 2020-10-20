CREATE TABLE orders (
  id bigint AUTO_INCREMENT,
  order_status int,
  date_time TIMESTAMP,
  user_id bigint,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) references usr(id)
);

CREATE TABLE orderpiece (
  id bigint AUTO_INCREMENT,
  quantity int,
  food_id bigint,
  order_id bigint,
  PRIMARY KEY (id),
  FOREIGN KEY (food_id) references food(id),
  FOREIGN KEY (order_id) references orders(id)
);
