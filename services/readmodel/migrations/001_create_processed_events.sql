CREATE TABLE IF NOT EXISTS processed_events (
                                                message_id VARCHAR(100) PRIMARY KEY,
    processed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );
