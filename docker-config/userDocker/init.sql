CREATE TABLE roles(
                      role_id int NOT NULL AUTO_INCREMENT,
                      role_name varchar(50),
                      PRIMARY KEY (role_id)
);

CREATE TABLE users (
                       user_id int NOT NULL AUTO_INCREMENT,
                       firstname varchar(50),
                       lastname varchar(50),
                       address varchar(50),
                       date_of_birth date,
                       email varchar(50) NOT NULL unique,
                       user_password varchar(500) NOT NULL,
                       phone varchar(50),
                       role_id int,
                       FOREIGN KEY (role_id) REFERENCES roles(role_id),
                       PRIMARY KEY (user_id)
);

INSERT INTO roles VALUES (1, 'USER');