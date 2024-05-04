-- Create tables if not exists
CREATE TABLE IF NOT EXISTS persons
(
    id       UUID PRIMARY KEY,
    owner    UUID NOT NULL,
    name     VARCHAR(255) NOT NULL,
    lastname VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS occasions
(
    id        UUID PRIMARY KEY,
    owner     UUID NOT NULL,
    name      VARCHAR(255) NOT NULL,
    date      TIMESTAMP,
    person_id UUID,
    CONSTRAINT fk_person_id_occ FOREIGN KEY (person_id) REFERENCES persons (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reminders
(
    id          UUID PRIMARY KEY,
    owner       UUID NOT NULL,
    name        VARCHAR(255) NOT NULL,
    occasion_id UUID,
    CONSTRAINT fk_occasion_id_rem FOREIGN KEY (occasion_id) REFERENCES occasions (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS presents_purchased
(
    id          UUID PRIMARY KEY,
    owner       UUID NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    price       DECIMAL(10, 2),
    person_id   UUID,
    CONSTRAINT fk_person_id_pp FOREIGN KEY (person_id) REFERENCES persons (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS present_ideas
(
    id          UUID PRIMARY KEY,
    owner       UUID NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    price       DECIMAL(10, 2),
    person_id   UUID,
    occasion_id UUID,
    CONSTRAINT fk_person_id_pi FOREIGN KEY (person_id) REFERENCES persons (id) ON DELETE CASCADE,
    CONSTRAINT fk_occasion_id_pi FOREIGN KEY (occasion_id) REFERENCES occasions (id) ON DELETE CASCADE
);
