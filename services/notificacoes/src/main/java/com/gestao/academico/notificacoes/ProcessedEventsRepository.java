package com.gestao.academico.notificacoes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProcessedEventsRepository {
    private final JdbcTemplate jdbc;
    private final Logger logger = LoggerFactory.getLogger(ProcessedEventsRepository.class);

    public ProcessedEventsRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public boolean exists(String messageId) {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(1) FROM processed_events WHERE message_id = ?",
                Integer.class, messageId);
        return count != null && count > 0;
    }

    public void register(String messageId) {
        try {
            jdbc.update("INSERT INTO processed_events(message_id) VALUES(?)", messageId);
        } catch (DuplicateKeyException ex) {
            // already processed — ignore
            logger.debug("message already processed: {}", messageId);
        }
    }
}
