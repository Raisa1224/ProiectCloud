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

--
INSERT INTO roles VALUES (1, 'USER');