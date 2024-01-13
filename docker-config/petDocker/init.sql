CREATE TABLE users (
                       user_id int NOT NULL AUTO_INCREMENT,
                       firstname varchar(50),
                       lastname varchar(50),
                       PRIMARY KEY (user_id)
);

CREATE TABLE breed (
                       breed_id int NOT NULL AUTO_INCREMENT,
                       breed_name varchar(50) NOT NULL,
                       breed_description varchar(500),
                       lifespan int,
                       PRIMARY KEY (breed_id)
);

CREATE TABLE species (
                         species_id int NOT NULL AUTO_INCREMENT,
                         species_name varchar(50) NOT NULL,
                         species_description varchar(500),
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
