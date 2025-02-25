package ru.job4j.cinema.repository.sql2o;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.ThreadSafe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.Optional;

@ThreadSafe
@Repository
@RequiredArgsConstructor
public class Sql2oTicketRepository implements TicketRepository {

    private final Sql2o sql2o;

    @Override
    public Ticket save(Ticket ticket) {
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
            return ticket;
        } catch (Exception e) {
            throw new RuntimeException("Выберите другое место", e);
        }
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
    public void deleteById(Long id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                            """
                                    DELETE FROM tickets WHERE id = :id
                                    """)
                    .addParameter("id", id);
            var affectedRows = query.executeUpdate().getResult();
            if (affectedRows == 0) {
                throw new RuntimeException("Не удалось удалить билет с id : " + id);
            }
        }
    }
}
