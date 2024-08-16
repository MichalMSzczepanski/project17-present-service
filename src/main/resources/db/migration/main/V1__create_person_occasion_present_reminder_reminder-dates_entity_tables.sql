-- Create tables if not exists
CREATE TABLE IF NOT EXISTS persons
(
    id         UUID PRIMARY KEY,
    owner      UUID         NOT NULL,
    name       VARCHAR(255) NOT NULL,
    lastname   VARCHAR(255),
    created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS occasions
(
    id           UUID PRIMARY KEY,
    owner        UUID         NOT NULL,
    name         VARCHAR(255) NOT NULL,
    date         TIMESTAMP,
    fk_person_id UUID,
    created_at   TIMESTAMP,
    CONSTRAINT fk_person_id_occ FOREIGN KEY (fk_person_id) REFERENCES persons (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS presents
(
    id             UUID PRIMARY KEY,
    owner          UUID         NOT NULL,
    name           VARCHAR(255) NOT NULL,
    type           VARCHAR(255),
    description    TEXT,
    price          DECIMAL(10, 2),
    fk_occasion_id UUID,
    created_at     TIMESTAMP,
    CONSTRAINT fk_occasion_id_pre FOREIGN KEY (fk_occasion_id) REFERENCES occasions (id) ON DELETE CASCADE
);

-- Create reminders table
CREATE TABLE IF NOT EXISTS reminders
(
    id             UUID PRIMARY KEY,
    owner          UUID         NOT NULL,
    name           VARCHAR(255) NOT NULL,
    recurring      VARCHAR(255) NOT NULL,
    created_at     TIMESTAMP,
    fk_occasion_id UUID,
    CONSTRAINT fk_occasion_id_rem FOREIGN KEY (fk_occasion_id) REFERENCES occasions (id) ON DELETE CASCADE
);

-- Create reminder_dates table with foreign key
CREATE TABLE IF NOT EXISTS reminder_dates
(
    id             UUID PRIMARY KEY,
    fk_reminder_id UUID      NOT NULL,
    date           TIMESTAMP NOT NULL,
    created_at     TIMESTAMP,
    CONSTRAINT fk_reminder_id_dates FOREIGN KEY (fk_reminder_id) REFERENCES reminders (id) ON DELETE CASCADE
);