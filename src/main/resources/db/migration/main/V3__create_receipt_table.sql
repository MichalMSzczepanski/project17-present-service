CREATE TABLE IF NOT EXISTS receipts
(
    id                      UUID PRIMARY KEY,
    fk_present_idea_id      UUID         NOT NULL,
    fk_present_purchased_id UUID,
    image_url               VARCHAR(255) NOT NULL,
    created_at              TIMESTAMP,
    CONSTRAINT fk_present_id_rec FOREIGN KEY (fk_present_idea_id) REFERENCES present_ideas (id) ON DELETE CASCADE,
    CONSTRAINT fk_present_purchased_id_rec FOREIGN KEY (fk_present_purchased_id) REFERENCES presents_purchased (id) ON DELETE CASCADE
);