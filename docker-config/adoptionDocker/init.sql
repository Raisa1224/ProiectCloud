CREATE TABLE users (
                       user_id int NOT NULL AUTO_INCREMENT,
                       firstname varchar(50),
                       lastname varchar(50),
                       address varchar(50),
                       email varchar(50) NOT NULL unique,
                       PRIMARY KEY (user_id)
);

CREATE TABLE breed (
                       breed_id int NOT NULL AUTO_INCREMENT,
                       PRIMARY KEY (breed_id)
);

CREATE TABLE species (
                         species_id int NOT NULL AUTO_INCREMENT,
                         PRIMARY KEY (species_id)
);

CREATE TABLE pet (
                     pet_id int NOT NULL AUTO_INCREMENT,
                     pet_name varchar(50) NOT NULL,
                     species_id int,
                     breed_id int,
                     year_of_birth int,
                     gender varchar(2),
                     color varchar(50),
                     price double,
                     available boolean,
                     owner_id int,
                     PRIMARY KEY (pet_id),
                     FOREIGN KEY (species_id) REFERENCES species(species_id),
                     FOREIGN KEY (breed_id) REFERENCES breed(breed_id),
                     FOREIGN KEY (owner_id) REFERENCES users(user_id)
);

CREATE TABLE adoption_request (
                                  adoption_request_id int NOT NULL AUTO_INCREMENT,
                                  client_id int,
                                  pet_id int,
                                  adoption_date date,
                                  comments varchar(500),
                                  PRIMARY KEY (adoption_request_id),
                                  FOREIGN KEY (client_id) REFERENCES users(user_id),
                                  FOREIGN KEY (pet_id) REFERENCES pet(pet_id)
);

CREATE TABLE payment (
                         payment_id int NOT NULL AUTO_INCREMENT,
                         adoption_request_id int,
                         payment_date date,
                         amount double,
                         PRIMARY KEY (payment_id),
                         FOREIGN KEY (adoption_request_id) REFERENCES adoption_request(adoption_request_id)
);

CREATE TABLE adoption_feedback (
                                   feedback_id int NOT NULL AUTO_INCREMENT,
                                   adoption_request_id int,
                                   feedback_date date,
                                   content varchar(500),
                                   rating int,
                                   PRIMARY KEY (feedback_id),
                                   FOREIGN KEY (adoption_request_id) REFERENCES adoption_request(adoption_request_id)
);