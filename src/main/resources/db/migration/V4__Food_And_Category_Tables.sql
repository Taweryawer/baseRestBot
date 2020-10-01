CREATE TABLE category (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(255),
  PRIMARY KEY (id)
);

CREATE TABLE food (
  id int NOT NULL AUTO_INCREMENT,
  title varchar(255),
  weight varchar(255),
  description varchar(255),
  photoURL varchar(255),
  price_kyiv int(11),
  price_kharkiv int(11),
  category_id int(11),
  PRIMARY KEY (id),
  FOREIGN KEY (category_id) REFERENCES category(id)
)