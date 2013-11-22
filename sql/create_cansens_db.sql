DROP DATABASE cansensdb;
CREATE DATABASE cansensdb DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE cansensdb;

CREATE TABLE goods(
	id INT NOT NULL AUTO_INCREMENT,
	good_name VARCHAR(255),
	good_id VARCHAR(255),
	good_bn VARCHAR(255),
	good_unit VARCHAR(15),
	good_price VARCHAR(15),
	good_desc TEXT,
	good_img_url VARCHAR(255),
	good_img_filename VARCHAR(255),

	PRIMARY KEY(id)) DEFAULT CHARSET = utf8;






