CREATE TABLE IF NOT EXISTS presents_purchased
(
    id             UUID PRIMARY KEY,
    owner          UUID         NOT NULL,
    name           VARCHAR(255) NOT NULL,
    description    TEXT,
    price          DECIMAL(10, 2),
    image_url      VARCHAR(255),
    fk_person_id   UUID,
    created_at     TIMESTAMP,
    CONSTRAINT fk_person_id_pre FOREIGN KEY (fk_person_id) REFERENCES persons (id) ON DELETE CASCADE
);
