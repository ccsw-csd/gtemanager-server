CREATE SCHEMA personal AUTHORIZATION sa; 

CREATE TABLE personal.t_person (
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



CREATE TABLE center (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO center (name) VALUES ('Madrid');
INSERT INTO center (name) VALUES ('Barcelona');
INSERT INTO center (name) VALUES ('Valencia');



CREATE TABLE center_transcode (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  center_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id)
);



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
INSERT INTO person (saga,username,email,name,lastname,center,businesscode,grade,active) VALUES ('A1','aelmouss','aelmouss@example.com','Ayoub','El Moussaoui','3', 'XXX', 'A', true);
INSERT INTO person (saga,username,email,name,lastname,center,businesscode,grade,active) VALUES ('A2','jopepe','jopepe@example.com','Jopepe','Jopepe','3', 'XXX', 'B', false);
INSERT INTO person (saga,username,email,name,lastname,center,businesscode,grade,active) VALUES ('A3','amirzoya','amirzoya@example.com','Armen','Mirzoyan Denisov','3', 'XXX', 'C', true);
INSERT INTO person (saga,username,email,name,lastname,center,businesscode,grade,active) VALUES ('A4','pajimene','pajimene@example.com','Pablo','Jimenez Martinez','3', 'XXX', 'D', true);

CREATE TABLE person_saga_transcode (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  person_id int(11) NOT NULL,
  saga varchar(50) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT person_saga_person_fk FOREIGN KEY (person_id) REFERENCES person (id)
);

INSERT INTO person_saga_transcode (person_id, saga) VALUES ('3', 'B2');

CREATE TABLE person_email_transcode (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  person_id int(11) NOT NULL,
  email varchar(100) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT person_email_person_fk FOREIGN KEY (person_id) REFERENCES person (id)
);

INSERT INTO person_email_transcode (person_id, email) VALUES (1, 'anacardo@example.com');
INSERT INTO person_email_transcode (person_id, email) VALUES (2, 'aitortilla@example.com');
INSERT INTO person_email_transcode (person_id, email) VALUES (3, 'aelmouss@example.com');
INSERT INTO person_email_transcode (person_id, email) VALUES (4, 'jopepe@example.com');
INSERT INTO person_email_transcode (person_id, email) VALUES (5, 'amirzoya@example.com');
INSERT INTO person_email_transcode (person_id, email) VALUES (6, 'pajimene@example.com');

CREATE TABLE evidence (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  person_id int(11) NOT NULL,
  saga varchar(25),
  evidente_type_w1 int(11),
  evidente_type_w2 int(11),
  evidente_type_w3 int(11),
  evidente_type_w4 int(11),
  evidente_type_w5 int(11),
  evidente_type_w6 int(11),
  email_notification_sent tinyint(1) DEFAULT 0 NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT evidence_person_fk FOREIGN KEY (person_id) REFERENCES person (id)
);

INSERT INTO evidence (person_id, saga, evidente_type_w1, evidente_type_w2, evidente_type_w3, evidente_type_w4, evidente_type_w5, evidente_type_w6, email_notification_sent) VALUES (1, '1', null, null, null, 3, null, null, 1);
INSERT INTO evidence (person_id, saga, evidente_type_w1, evidente_type_w2, evidente_type_w3, evidente_type_w4, evidente_type_w5, evidente_type_w6, email_notification_sent) VALUES (2, '2', null, null, null, 3, null, null, 0);
INSERT INTO evidence (person_id, saga, evidente_type_w1, evidente_type_w2, evidente_type_w3, evidente_type_w4, evidente_type_w5, evidente_type_w6, email_notification_sent) VALUES (3, '3', null, null, 2, 3, null, null, 0);
INSERT INTO evidence (person_id, saga, evidente_type_w1, evidente_type_w2, evidente_type_w3, evidente_type_w4, evidente_type_w5, evidente_type_w6, email_notification_sent) VALUES (4, '4', null, null, 2, 3, null, null, 0);
INSERT INTO evidence (person_id, saga, evidente_type_w1, evidente_type_w2, evidente_type_w3, evidente_type_w4, evidente_type_w5, evidente_type_w6, email_notification_sent) VALUES (5, '5', null, 3, null, 3, null, null, 0);
INSERT INTO evidence (person_id, saga, evidente_type_w1, evidente_type_w2, evidente_type_w3, evidente_type_w4, evidente_type_w5, evidente_type_w6, email_notification_sent) VALUES (6, '6', 1, null, 2, 3, 2, null, 0);

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
	error_message varchar(4000) NULL,
	PRIMARY KEY (id)
);

INSERT INTO evidence_error (name, saga, email, period, status, error_message) VALUES ('P1', 'S1', 'E1', 'P1', 'S1', 'E1');
INSERT INTO evidence_error (name, saga, email, period, status, error_message) VALUES ('P2', 'S2', 'E2', 'P2', 'S2', 'E2');
INSERT INTO evidence_error (name, saga, email, period, status, error_message) VALUES ('P3', 'S3', 'E3', 'P3', 'S3', 'E3');


CREATE TABLE properties (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	key varchar(20) NOT NULL,
	value varchar(400),
	PRIMARY KEY (id)
);

INSERT INTO properties (key, value) VALUES ('LOAD_DATE', '27/07/2022 08:30');
INSERT INTO properties (key, value) VALUES ('LOAD_USERNAME', 'user');
INSERT INTO properties (key, value) VALUES ('LOAD_WEEKS', '5');
INSERT INTO properties (key, value) VALUES ('WEEK_1', '01-AUG-2022 - 07-AUG-2022');
INSERT INTO properties (key, value) VALUES ('WEEK_2', '08-AUG-2022 - 14-AUG-2022');
INSERT INTO properties (key, value) VALUES ('WEEK_3', '15-AUG-2022 - 21-AUG-2022');
INSERT INTO properties (key, value) VALUES ('WEEK_4', '22-AUG-2022 - 28-AUG-2022');
INSERT INTO properties (key, value) VALUES ('WEEK_5', '29-AUG-2022 - 04-SEP-2022');
INSERT INTO properties (key, value) VALUES ('WEEK_6' , null);

DROP TABLE IF EXISTS evidence_comment;

CREATE TABLE evidence_comment (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  person_id bigint(20) NOT NULL,
  comment varchar(400),
  last_edit_date DATE,
  last_edit_author varchar(100),
  PRIMARY KEY (id),
  CONSTRAINT evidence_comment_fk FOREIGN KEY (person_id) REFERENCES person (id)
);

INSERT INTO evidence_comment (person_id, comment, last_edit_date, last_edit_author) VALUES ('1', 'C1','2022-01-01','Author1');
INSERT INTO evidence_comment (person_id, comment, last_edit_date, last_edit_author) VALUES ('2', 'C2','2022-05-01','Author2');

CREATE TABLE v_evidence_with_comment (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  saga varchar(25),
  person_id int(11) NOT NULL,
  comment_id bigint(20),
  manager varchar(4000),
  project varchar(4000),
  row_color varchar(400),
  evidente_type_w1 int(11),
  evidente_type_w2 int(11),
  evidente_type_w3 int(11),
  evidente_type_w4 int(11),
  evidente_type_w5 int(11),
  evidente_type_w6 int(11),
  email_notification_sent int(1),
  PRIMARY KEY (id)
);

INSERT INTO v_evidence_with_comment (saga, person_id, comment_id, manager, project, row_color, evidente_type_w1, evidente_type_w2, evidente_type_w3, evidente_type_w4, evidente_type_w5, evidente_type_w6, email_notification_sent) VALUES ('1', 1, 1, 'comment', 'manager', 'color', null, null, null, 3, null, null, 0);
INSERT INTO v_evidence_with_comment (saga, person_id, comment_id, manager, project, row_color, evidente_type_w1, evidente_type_w2, evidente_type_w3, evidente_type_w4, evidente_type_w5, evidente_type_w6, email_notification_sent) VALUES ('2', 2, null, 'comment', 'manager', 'color', null, null, null, 3, null, null, 0);
INSERT INTO v_evidence_with_comment (saga, person_id, comment_id, manager, project, row_color, evidente_type_w1, evidente_type_w2, evidente_type_w3, evidente_type_w4, evidente_type_w5, evidente_type_w6, email_notification_sent) VALUES ('3', 3, null, 'comment', 'manager', 'color', null, null, 2, 3, null, null, 0);
INSERT INTO v_evidence_with_comment (saga, person_id, comment_id, manager, project, row_color, evidente_type_w1, evidente_type_w2, evidente_type_w3, evidente_type_w4, evidente_type_w5, evidente_type_w6, email_notification_sent) VALUES ('4', 4, null, 'comment', 'manager', 'color', null, null, 2, 3, null, null, 0);
INSERT INTO v_evidence_with_comment (saga, person_id, comment_id, manager, project, row_color, evidente_type_w1, evidente_type_w2, evidente_type_w3, evidente_type_w4, evidente_type_w5, evidente_type_w6, email_notification_sent) VALUES ('5', 5, null, 'comment', 'manager', 'color', null, 3, null, 3, null, null, 0);
INSERT INTO v_evidence_with_comment (saga, person_id, comment_id, manager, project, row_color, evidente_type_w1, evidente_type_w2, evidente_type_w3, evidente_type_w4, evidente_type_w5, evidente_type_w6, email_notification_sent) VALUES ('6', 6, null, 'comment', 'manager', 'color', 1, null, 2, 3, 2, null, 0);








