
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
  UNIQUE KEY email (email),
  CONSTRAINT user_role_fk FOREIGN KEY (role) REFERENCES role (id)
);

INSERT INTO user (id,username,email,first_name,last_name,role) VALUES (1,'USERNAME','USER@USER.COM','NAME','LASTNAME',1);
INSERT INTO user (id,username,email,first_name,last_name,role) VALUES (2,'USERNAME2','USER2@USER.COM','NAME','LASTNAME',1);
INSERT INTO user (id,username,email,first_name,last_name,role) VALUES (3,'USERNAME3','USER3@USER.COM','NAME','LASTNAME',1);
INSERT INTO user (id,username,email,first_name,last_name,role) VALUES (4,'USERNAME4','USER4@USER.COM','NAME','LASTNAME',1);
INSERT INTO user (id,username,email,first_name,last_name,role) VALUES (5,'USERNAME5','USER5@USER.COM','NAME','LASTNAME',1);
INSERT INTO user (id,username,email,first_name,last_name,role) VALUES (6,'USERNAME6','USER6@USER.COM','NAME6','LASTNAME6',1);

CREATE TABLE opportunity_status (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(20) NOT NULL,
  priority int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY st_name (name)
);


INSERT INTO opportunity_status (name,priority) VALUES ('Otros', 2);
INSERT INTO opportunity_status (name,priority) VALUES ('Otros2', 1);
INSERT INTO opportunity_status (name,priority) VALUES ('Otros3', 4);
INSERT INTO opportunity_status (name,priority) VALUES ('Otros4', 3);

CREATE TABLE opportunity_type (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(20) NOT NULL,
  priority int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY ty_name (name)
);

INSERT INTO opportunity_type (name,priority) VALUES ('Otros', 1);
INSERT INTO opportunity_type (name,priority) VALUES ('Otros2', 4);
INSERT INTO opportunity_type (name,priority) VALUES ('Otros3', 2);
INSERT INTO opportunity_type (name,priority) VALUES ('Otros4', 3);


CREATE TABLE sector (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(20) NOT NULL,
  priority int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY se_name (name)
);

INSERT INTO sector (name,priority) VALUES ('Otros', 2);
INSERT INTO sector (name,priority) VALUES ('Otros2', 4);
INSERT INTO sector (name,priority) VALUES ('Otros3', 3);
INSERT INTO sector (name,priority) VALUES ('Otros4', 1);

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

INSERT INTO person (saga,username,email,name,lastname,center,businesscode,grade,active) VALUES ('A1','aelmouss','aelmouss@bidoffice.com','Ayoub','El Moussaoui','VLC', 'XXX', 'A', true);
INSERT INTO person (saga,username,email,name,lastname,center,businesscode,grade,active) VALUES ('A2','jopepe','jopepe@bidoffice.com','Jopepe','Jopepe','VLC', 'XXX', 'B', false);
INSERT INTO person (saga,username,email,name,lastname,center,businesscode,grade,active) VALUES ('A3','amirzoya','amirzoya@bidoffice.com','Armen','Mirzoyan Denisov','VLC', 'XXX', 'C', true);
INSERT INTO person (saga,username,email,name,lastname,center,businesscode,grade,active) VALUES ('A4','pajimene','amirzoya@bidoffice.com','Pablo','Jimenez Martinez','VLC', 'XXX', 'D', true);

CREATE TABLE file_type (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(20) NOT NULL,
  priority int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY file_name (name)
);

INSERT INTO file_type (name,priority) VALUES ('Otros', 4);
INSERT INTO file_type (name,priority) VALUES ('Otros2', 3);
INSERT INTO file_type (name,priority) VALUES ('Otros3', 2);
INSERT INTO file_type (name,priority) VALUES ('Otros4', 1);


CREATE TABLE methodology (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(20) NOT NULL,
  priority int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY metho_name (name)
);

INSERT INTO methodology (name,priority) VALUES ('Otros', 1);
INSERT INTO methodology (name,priority) VALUES ('Otros2', 3);
INSERT INTO methodology (name,priority) VALUES ('Otros3', 1);
INSERT INTO methodology (name,priority) VALUES ('Otros4', 2);

CREATE TABLE offering (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(20) NOT NULL,
  priority int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY offering_name (name)
);

INSERT INTO offering (name,priority) VALUES ('Otros', 2);
INSERT INTO offering (name,priority) VALUES ('Otros2', 4);
INSERT INTO offering (name,priority) VALUES ('Otros3', 3);
INSERT INTO offering (name,priority) VALUES ('Otros4', 1);

CREATE TABLE project_type (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(20) NOT NULL,
  priority int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY project_name (name)
);

INSERT INTO project_type (name,priority) VALUES ('Otros', 1);
INSERT INTO project_type (name,priority) VALUES ('Otros2', 2);
INSERT INTO project_type (name,priority) VALUES ('Otros3', 4);
INSERT INTO project_type (name,priority) VALUES ('Otros4', 3);


CREATE TABLE offer (
  id int(11) NOT NULL AUTO_INCREMENT,
  client varchar(50) NOT NULL,
  name varchar(50) NOT NULL,
  requested_by bigint(20) DEFAULT NULL,
  requested_date date NOT NULL,
  managed_by bigint(20) DEFAULT NULL,
  bdc_code varchar(50) DEFAULT NULL,
  sector_id int(11) NOT NULL,
  go_nogo_date date DEFAULT NULL,
  delivery_date date DEFAULT NULL,
  opportunity_status_id int(11) NOT NULL,
  opportunity_type_id int(11) NOT NULL,
  opportunity_win int(1) DEFAULT NULL,
  observations varchar(4000) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT offer_managed_by_fk FOREIGN KEY (managed_by) REFERENCES person (id),
  CONSTRAINT offer_opportunity_status_fk FOREIGN KEY (opportunity_status_id) REFERENCES opportunity_status (id),
  CONSTRAINT offer_opportunity_type_fk FOREIGN KEY (opportunity_type_id) REFERENCES opportunity_type (id),
  CONSTRAINT offer_request_by_fk FOREIGN KEY (requested_by) REFERENCES person (id),
  CONSTRAINT offer_sector_fk FOREIGN KEY (sector_id) REFERENCES sector (id)
);


INSERT INTO offer (client,name,requested_date,sector_id,opportunity_status_id,opportunity_type_id) VALUES ('user', 'user', '2022-06-30',2,1,1);
INSERT INTO offer (client,name,requested_date,sector_id,opportunity_status_id,opportunity_type_id) VALUES ('user', 'user', '2022-06-30',2,1,1);
INSERT INTO offer (client,name,requested_date,sector_id,opportunity_status_id,opportunity_type_id) VALUES ('user2', 'user', '2022-06-30',2,2,1);
INSERT INTO offer (client,name,requested_date,sector_id,opportunity_status_id,opportunity_type_id) VALUES ('user3', 'user', '2022-06-30',2,1,1);
INSERT INTO offer (client,name,requested_date,sector_id,opportunity_status_id,opportunity_type_id) VALUES ('user4', 'user', '2022-06-30',2,2,1);



CREATE TABLE hyperscaler(
	id bigint NOT NULL AUTO_INCREMENT,
	name varchar(50) NOT NULL,
	priority int NOT NULL,
	PRIMARY KEY (id)
);

INSERT INTO hyperscaler (name,priority) VALUES ('Name 1',1);
INSERT INTO hyperscaler (name,priority) VALUES ('Name 2',2);
INSERT INTO hyperscaler (name,priority) VALUES ('Name 3',3);

DROP TABLE IF EXISTS file_type;

CREATE TABLE file_type (
	id bigint NOT NULL AUTO_INCREMENT,
  	name varchar(50) NOT NULL,
  	priority bigint NOT NULL,
  	PRIMARY KEY (id)
);

INSERT INTO file_type (name,priority) VALUES ( 'user1', 1);
INSERT INTO file_type (name,priority) VALUES ( 'user2', 2);
INSERT INTO file_type (name,priority) VALUES ( 'user3', 3);

CREATE TABLE offer_data_chapter (
	id bigint NOT NULL AUTO_INCREMENT,
 	offer_id int(11) NOT NULL,
	presentation int(1) DEFAULT NULL,
	capabilities int(1) DEFAULT NULL,
	approach int(1) DEFAULT NULL,
	methodology int(1) DEFAULT NULL,
	work_model int(1) DEFAULT NULL,
	team int(1) DEFAULT NULL,
	planning int(1) DEFAULT NULL,
	value_added int(1) DEFAULT NULL,
	innovation int(1) DEFAULT NULL,
	references int(1) DEFAULT NULL,
	key_document int(1) DEFAULT NULL,
	PRIMARY KEY (id),
 	CONSTRAINT offer_offer_fk8 FOREIGN KEY (offer_id) REFERENCES offer (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO offer_data_chapter (offer_id, presentation, capabilities, approach) VALUES (1,0,0,1);

CREATE TABLE offer_data_files (
 	id int(11) NOT NULL AUTO_INCREMENT,
	offer_id int(11) NOT NULL,
	name varchar(100) NOT NULL,
	file_type_id int(11) NOT NULL,
 	link varchar(4000) NOT NULL,
 	observations varchar(4000) DEFAULT NULL,
	PRIMARY KEY (id),
	CONSTRAINT offer_file_fk FOREIGN KEY (file_type_id) REFERENCES file_type (id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT offer_offer_fk5 FOREIGN KEY (offer_id) REFERENCES offer (id) ON DELETE CASCADE ON UPDATE CASCADE
); 

INSERT INTO offer_data_files (offer_id,name,file_type_id,link) VALUES (1,'admin',1,'admin.com');
INSERT INTO offer_data_files (offer_id,name,file_type_id,link) VALUES (1,'jopepe',2,'jopepe.com');

CREATE TABLE offer_data_project (
	id int(11) NOT NULL AUTO_INCREMENT,
	offer_id int(11) NOT NULL,
	project_type_id int(11) NOT NULL,
	amount decimal(10,2) DEFAULT NULL,
	ftes decimal(10,2) DEFAULT NULL,
	months decimal(10,2) DEFAULT NULL,
	PRIMARY KEY (id),
 	CONSTRAINT offer_offer_fk4 FOREIGN KEY (offer_id) REFERENCES offer (id) ON DELETE CASCADE ON UPDATE CASCADE,
 	CONSTRAINT offer_project_fk FOREIGN KEY (project_type_id) REFERENCES project_type (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO offer_data_project (offer_id,project_type_id) VALUES (1,1);

CREATE TABLE offer_data_team (
	id int(11) NOT NULL AUTO_INCREMENT,
	offer_id int(11) NOT NULL,
	cca int(1) DEFAULT NULL,
	multitower int(1) DEFAULT NULL,
	practices varchar(4000) DEFAULT NULL,
	PRIMARY KEY (id),
	CONSTRAINT offer_offer_fk7 FOREIGN KEY (offer_id) REFERENCES offer (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO offer_data_team (offer_id, cca, multitower) VALUES (1,0,1);

CREATE TABLE offer_data_technology (
	id int(11) NOT NULL AUTO_INCREMENT,
	offer_id int(11) NOT NULL,
	hyperscaler_id int(11) DEFAULT NULL,
	methodology_id int(11) DEFAULT NULL,
	observations varchar(4000) DEFAULT NULL,
	PRIMARY KEY (id),
	CONSTRAINT offer_hyperscaler_fk FOREIGN KEY (hyperscaler_id) REFERENCES hyperscaler (id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT offer_methodology_fk FOREIGN KEY (methodology_id) REFERENCES methodology (id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT offer_offer_fk3 FOREIGN KEY (offer_id) REFERENCES offer (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO offer_data_technology (offer_id,hyperscaler_id,methodology_id,observations) VALUES (1,1,1,'observations');

CREATE TABLE offer_offering (
	id int(11) NOT NULL AUTO_INCREMENT,
	offer_id int(11) NOT NULL,
	offering_id int(11) NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT offer_offer_fk FOREIGN KEY (offer_id) REFERENCES offer (id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT offer_offering_fk FOREIGN KEY (offering_id) REFERENCES offering (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO offer_offering (offer_id,offering_id) VALUES (1,1);
INSERT INTO offer_offering (offer_id,offering_id) VALUES (1,1);

CREATE TABLE offer_team_person (
	id int(11) NOT NULL AUTO_INCREMENT,
	offer_id int(11) NOT NULL,
	person_id bigint(20) NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT offer_offer_fk6 FOREIGN KEY (offer_id) REFERENCES offer (id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT offer_team_person_fk FOREIGN KEY (person_id) REFERENCES person (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO offer_team_person (offer_id,person_id) VALUES (1,1);
INSERT INTO offer_team_person (offer_id,person_id) VALUES (1,1);

CREATE TABLE technology (
	id int(11) NOT NULL AUTO_INCREMENT,
	name varchar(20) NOT NULL,
	priority int(11) NOT NULL,
  	PRIMARY KEY (id),
  	UNIQUE KEY name (name)
);

INSERT INTO technology (name,priority) VALUES ('admin',1);

CREATE TABLE offer_technology (
	id int(11) NOT NULL AUTO_INCREMENT,
	offer_id int(11) NOT NULL,
	technology_id int(11) NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT offer_offer_fk2 FOREIGN KEY (offer_id) REFERENCES offer (id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT offer_technology_fk FOREIGN KEY (technology_id) REFERENCES technology (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO offer_technology (offer_id,technology_id) VALUES (1,1);
INSERT INTO offer_technology (offer_id,technology_id) VALUES (1,1);

CREATE TABLE offer_tracing (
	id int(11) NOT NULL AUTO_INCREMENT,
	offer_id int(11) NOT NULL,
	person_id bigint(20) NOT NULL,
	comment varchar(4000) NOT NULL,
	date date NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT offer_tracing_fk FOREIGN KEY (offer_id) REFERENCES offer (id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT offer_tracing_person_fk FOREIGN KEY (person_id) REFERENCES person (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO offer_tracing (offer_id,person_id,comment,date) VALUES (1,1,'comment','2022-07-19');
INSERT INTO offer_tracing (offer_id,person_id,comment,date) VALUES (1,1, 'comment2','2022-07-19');
