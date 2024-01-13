
CREATE TABLE pet (
                     pet_id int NOT NULL AUTO_INCREMENT,
                     PRIMARY KEY (pet_id)
);

CREATE TABLE pet_vaccinations (
                                  vaccination_id int NOT NULL AUTO_INCREMENT,
                                  vaccination_name varchar(50) NOT NULL,
                                  vaccination_date date,
                                  dose int,
                                  total_doses int,
                                  pet_id int,
                                  PRIMARY KEY (vaccination_id),
                                  FOREIGN KEY (pet_id) REFERENCES pet(pet_id)
);

CREATE TABLE pet_medications (
                                 medication_id int NOT NULL AUTO_INCREMENT,
                                 medication_name varchar(50),
                                 reason varchar(50),
                                 dosage int,
                                 frequency_days int,
                                 observations varchar(500),
                                 pet_id int,
                                 PRIMARY KEY (medication_id),
                                 FOREIGN KEY (pet_id) REFERENCES pet(pet_id)
);

CREATE TABLE pet_veterinary_visits (
                                       visit_id int NOT NULL AUTO_INCREMENT,
                                       clinic varchar(50),
                                       visit_date date,
                                       cause varchar(500),
                                       result varchar(500),
                                       pet_id int,
                                       PRIMARY KEY (visit_id),
                                       FOREIGN KEY (pet_id) REFERENCES pet(pet_id)
);

CREATE TABLE pet_special_conditions (
                                        condition_id int NOT NULL AUTO_INCREMENT,
                                        condition_name varchar(50),
                                        condition_description varchar(500),
                                        observations varchar(500),
                                        pet_id int,
                                        PRIMARY KEY (condition_id),
                                        FOREIGN KEY (pet_id) REFERENCES pet(pet_id)
);