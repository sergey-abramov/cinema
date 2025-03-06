package ru.job4j.cinema.repository.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

@Repository
public interface JpaUserRep extends JpaRepository<User, Long>, UserRepository {
}
