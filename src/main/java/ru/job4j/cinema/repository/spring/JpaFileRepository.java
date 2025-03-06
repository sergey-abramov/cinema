package ru.job4j.cinema.repository.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.repository.FileRepository;

@Repository
public interface JpaFileRepository extends JpaRepository<File, Long>, FileRepository {
}
