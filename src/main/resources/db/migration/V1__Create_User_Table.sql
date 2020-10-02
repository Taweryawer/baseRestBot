CREATE TABLE usr (
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(255),
  address varchar(255),
  payment_method varchar(255),
  machine_id varchar(255) NOT NULL,
  phone_number varchar(255),
  PRIMARY KEY (id)
)