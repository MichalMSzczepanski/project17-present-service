CREATE TABLE IF NOT EXISTS receipts
(
    id            UUID PRIMARY KEY,
    fk_present_id UUID         NOT NULL,
    aws_s3_url           VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP,
    CONSTRAINT fk_present_id_rec FOREIGN KEY (fk_present_id) REFERENCES presents (id) ON DELETE CASCADE
);