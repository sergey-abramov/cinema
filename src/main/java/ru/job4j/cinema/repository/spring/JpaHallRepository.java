package ru.job4j.cinema.repository.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallRepository;

@Repository
public interface JpaHallRepository extends JpaRepository<Hall, Long>, HallRepository {
}
