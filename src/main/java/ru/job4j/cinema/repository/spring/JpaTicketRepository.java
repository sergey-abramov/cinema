package ru.job4j.cinema.repository.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

@Repository
public interface JpaTicketRepository extends JpaRepository<Ticket, Long> {

}
