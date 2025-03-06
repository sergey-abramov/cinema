package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.junit.platform.commons.function.Try;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.Optional;

@ThreadSafe
@Service
public class SimpleTicketService implements TicketService {

    private final TicketRepository repository;

    public SimpleTicketService(TicketRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        return Optional.ofNullable(repository.save(ticket));
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public boolean update(Ticket ticket) {
        return repository.update(ticket);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
