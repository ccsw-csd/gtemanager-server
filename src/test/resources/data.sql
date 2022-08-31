
CREATE TABLE role (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(20)  NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY ro_name (name)
);

INSERT INTO role (id,name) VALUES (1,'ADMIN');
INSERT INTO role (id,name) VALUES (2,'USER');

CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(100) NOT NULL,
  email varchar(100) NOT NULL,
  first_name varchar(100) DEFAULT NULL,
  last_name varchar(100) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY username (username),
  UNIQUE KEY email (email)
);

INSERT INTO user (id,username,email,first_name,last_name) VALUES (1,'user','USER@USER.COM','NAME','LASTNAME');
INSERT INTO user (id,username,email,first_name,last_name) VALUES (2,'USERNAME2','USER2@USER.COM','NAME','LASTNAME');
INSERT INTO user (id,username,email,first_name,last_name) VALUES (3,'USERNAME3','USER3@USER.COM','NAME','LASTNAME');
INSERT INTO user (id,username,email,first_name,last_name) VALUES (4,'USERNAME4','USER4@USER.COM','NAME','LASTNAME');
INSERT INTO user (id,username,email,first_name,last_name) VALUES (5,'USERNAME5','USER5@USER.COM','NAME','LASTNAME');
INSERT INTO user (id,username,email,first_name,last_name) VALUES (6,'USERNAME6','USER6@USER.COM','NAME6','LASTNAME6');

CREATE TABLE center (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO center (name) VALUES ('Madrid');
INSERT INTO center (name) VALUES ('Barcelona');
INSERT INTO center (name) VALUES ('Valencia');

CREATE TABLE person (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  saga varchar(25) NOT NULL,
  username varchar(25) NOT NULL,
  email varchar(100) NOT NULL,
  name varchar(50) NOT NULL,
  lastname varchar(100) NOT NULL,
  center varchar(20) DEFAULT NULL,
  businesscode varchar(50) DEFAULT NULL,
  grade varchar(5) DEFAULT NULL,
  active int(1) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO person (saga,username,email,name,lastname,center,businesscode,grade,active) VALUES ('1','anacardo','anacardo@example.com','Ana','Cardo','3', 'XXX', 'E', true);
INSERT INTO person (saga,username,email,name,lastname,center,businesscode,grade,active) VALUES ('00B1','aitortilla','aitortilla@example.com','Aitor','Tilla','3', 'XXX', 'E', true);
INSERT INTO person (saga,username,email,name,lastname,center,businesscode,grade,active) VALUES ('A1','aelmouss','aelmouss@bidoffice.com','Ayoub','El Moussaoui','3', 'XXX', 'A', true);
INSERT INTO person (saga,username,email,name,lastname,center,businesscode,grade,active) VALUES ('A2','jopepe','jopepe@bidoffice.com','Jopepe','Jopepe','3', 'XXX', 'B', false);
INSERT INTO person (saga,username,email,name,lastname,center,businesscode,grade,active) VALUES ('A3','amirzoya','amirzoya@bidoffice.com','Armen','Mirzoyan Denisov','3', 'XXX', 'C', true);
INSERT INTO person (saga,username,email,name,lastname,center,businesscode,grade,active) VALUES ('A4','pajimene','amirzoya@bidoffice.com','Pablo','Jimenez Martinez','3', 'XXX', 'D', true);

CREATE TABLE person_saga_transcode (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  person_id int(11) NOT NULL,
  saga varchar(50) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT person_saga_person_fk FOREIGN KEY (person_id) REFERENCES person (id)
);

INSERT INTO person_saga_transcode (person_id, saga) VALUES ('3', 'B2');

CREATE TABLE evidence (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  person_id int(11) NOT NULL,
  evidente_type_w1 int(11),
  evidente_type_w2 int(11),
  evidente_type_w3 int(11),
  evidente_type_w4 int(11),
  evidente_type_w5 int(11),
  evidente_type_w6 int(11),
  PRIMARY KEY (id),
  CONSTRAINT evidence_person_fk FOREIGN KEY (person_id) REFERENCES person (id)
);

CREATE TABLE evidence_type (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	code varchar(10) NOT NULL,
	name varchar(100),
	PRIMARY KEY (id)
);

INSERT INTO evidence_type (code, name) VALUES ('ERROR', 'Error');
INSERT INTO evidence_type (code, name) VALUES ('Missing', 'Missing');
INSERT INTO evidence_type (code, name) VALUES ('WORKING', 'Working');
INSERT INTO evidence_type (code, name) VALUES ('REJECTED', 'Rechazada');

CREATE TABLE evidence_error (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	name varchar(500) NOT NULL,
	saga varchar(50) NOT NULL,
	email varchar(500) NOT NULL,
	period varchar(100) NOT NULL,
	status varchar(50) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE properties (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	key varchar(20) NOT NULL,
	value varchar(400),
	PRIMARY KEY (id)
);
