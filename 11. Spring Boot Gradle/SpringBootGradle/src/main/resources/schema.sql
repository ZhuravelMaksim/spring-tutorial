CREATE TABLE IF NOT EXISTS  ROLE (
ID BIGINT auto_increment,
NAME VARCHAR(50),
PRIMARY KEY (ID)
);

INSERT INTO ROLE (ID, NAME) VALUES (1, 'ROLE_USER');
INSERT INTO ROLE (ID, NAME) VALUES (2, 'ROLE_ADMIN');

CREATE TABLE IF NOT EXISTS  "USER" (
ID BIGINT auto_increment,
NAME VARCHAR(50),
PASSWORD VARCHAR(100),
PRIMARY KEY (ID),
);

INSERT INTO "USER" (ID, NAME, PASSWORD) VALUES (1, 'user.simple','$2a$10$l/D6AGt8vYJG.cW/lIT44uy.TAYkV9UYJ8bPuGKBwuva/ERc9Ct4K');
INSERT INTO "USER" (ID, NAME, PASSWORD) VALUES (2, 'user.admin','$2a$10$l/D6AGt8vYJG.cW/lIT44uy.TAYkV9UYJ8bPuGKBwuva/ERc9Ct4K');

CREATE TABLE IF NOT EXISTS  USER_ROLE (
USER_ID BIGINT,
ROLE_ID BIGINT,
FOREIGN KEY (USER_ID) REFERENCES "USER"(ID),
FOREIGN KEY (ROLE_ID) REFERENCES ROLE(ID)
);

INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES (1, 1);
INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES (2, 2);
