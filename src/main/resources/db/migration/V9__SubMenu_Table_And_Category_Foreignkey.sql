CREATE TABLE submenu
(
    id    bigint AUTO_INCREMENT,
    title varchar(255),
    PRIMARY KEY (id)
);

ALTER TABLE category
    ADD COLUMN submenu_id bigint;

ALTER TABLE category
    ADD FOREIGN KEY (submenu_id) REFERENCES submenu (id);