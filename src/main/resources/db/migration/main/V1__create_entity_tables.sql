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

ALTER TABLE occasions
    ADD CONSTRAINT fk_person_id_occ_bi_directional FOREIGN KEY (fk_person_id) REFERENCES persons (id) ON DELETE CASCADE;
ALTER TABLE presents
    ADD CONSTRAINT fk_occasion_id_pre_bi_directional FOREIGN KEY (fk_occasion_id) REFERENCES occasions (id) ON DELETE CASCADE;
