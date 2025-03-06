package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.Optional;

@ThreadSafe
@Service
public class SimpleUserService implements UserService {

    private final UserRepository repository;

    public SimpleUserService(@Qualifier("jpaUserRep")UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> save(User user) {
        return Optional.ofNullable(repository.save(user));
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return repository.findByEmailAndPassword(email, password);
    }
}
