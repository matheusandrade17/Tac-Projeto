@Repository
public class ProcessedEventsRepository {

    private final JdbcTemplate jdbc;

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
        jdbc.update("INSERT INTO processed_events(message_id) VALUES(?)", messageId);
    }
}
