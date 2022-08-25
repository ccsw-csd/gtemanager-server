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
  role int(11) DEFAULT NULL,
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

INSERT INTO person (id,saga,username,email,name,lastname,center,businesscode,grade,active) VALUES (1,'A1','aelmouss','aelmouss@bidoffice.com','Ayoub','El Moussaoui',null, 'XXX', 'A', 1);
INSERT INTO person (id,saga,username,email,name,lastname,center,businesscode,grade,active) VALUES (2,'A3','amirzoya','amirzoya@bidoffice.com','Armen','Mirzoyan Denisov',null, 'XXX', 'C', 1);
INSERT INTO person (id,saga,username,email,name,lastname,center,businesscode,grade,active) VALUES (3,'A4','pajimene','amirzoya@bidoffice.com','Pablo','Jimenez Martinez',null, 'XXX', 'D', 1);