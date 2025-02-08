package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

@ThreadSafe
@Repository
public class Sql2oTicketRepository implements TicketRepository {

    private static final Logger LOG = LogManager.getLogger(Sql2oTicketRepository.class.getName());

    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    """
                            INSERT INTO tickets (session_id, row_number, place_number, user_id)
                                    VALUES (:session_id, :row_number, :place_number, :user_id)
                            """, true)
                    .addParameter("session_id", ticket.getSessionId())
                    .addParameter("row_number", ticket.getRowNumber())
                    .addParameter("place_number", ticket.getPlaceNumber())
                    .addParameter("user_id", ticket.getUserId());
            Long generatedId = query.executeUpdate().getKey(Long.class);
            ticket.setId(generatedId);
            return Optional.of(ticket);
        } catch (Exception e) {
            LOG.error("Выберите другое место", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    """
                            SELECT * FROM tickets WHERE id = :id
                            """)
                    .addParameter("id", id);
            var ticket = query.setColumnMappings(Ticket.COLUMN_MAPPING)
                    .executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        }
    }

    @Override
    public boolean update(Ticket ticket) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    """
                            UPDATE tickets
                            SET session_id = :session_id, row_number = :row_number, 
                            place_number = :place_number, user_id = user_id
                            WHERE id = :id
                            """
            )
                    .addParameter("session_id", ticket.getSessionId())
                    .addParameter("row_number", ticket.getRowNumber())
                    .addParameter("place_number", ticket.getPlaceNumber())
                    .addParameter("user_id", ticket.getUserId())
                    .addParameter("id", ticket.getId());
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                            """
                                    DELETE FROM tickets WHERE id = :id
                                    """)
                    .addParameter("id", id);
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }
}
