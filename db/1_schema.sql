USE visitcounter;

DROP TABLE IF EXISTS visit_count;
CREATE TABLE visit_count (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    page_key VARCHAR(255) NOT NULL,
    count BIGINT NOT NULL DEFAULT 0,
    UNIQUE KEY uk_page_key (page_key)
);

