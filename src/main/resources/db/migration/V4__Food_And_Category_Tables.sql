CREATE TABLE category (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name varchar(255),
  PRIMARY KEY (id)
);

CREATE TABLE food (
  id bigint NOT NULL AUTO_INCREMENT,
  title varchar(255),
  weight varchar(255),
  description varchar(255),
  photoURL varchar(255),
  category_id bigint,
  PRIMARY KEY (id),
  FOREIGN KEY (category_id) REFERENCES category(id)
)