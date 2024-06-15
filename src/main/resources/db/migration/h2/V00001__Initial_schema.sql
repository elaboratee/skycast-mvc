CREATE TABLE IF NOT EXISTS User_Entity (
    id IDENTITY,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(60) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Chosen_City (
    id IDENTITY,
    user_id BIGINT NOT NULL,
    city_name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

-- Добавлнение внешних ключей
ALTER TABLE Chosen_City
    ADD FOREIGN KEY (user_id) REFERENCES User_Entity(id);

-- Добавление других ограничений
ALTER TABLE User_Entity
    ADD CONSTRAINT CK_USERNAME_LENGTH CHECK (LENGTH(username) BETWEEN 1 AND 20);

ALTER TABLE User_Entity
    ADD CONSTRAINT UQ_USERNAME UNIQUE (username);