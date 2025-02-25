package ru.job4j.cinema.repository.sql2o;

import net.jcip.annotations.ThreadSafe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.Optional;

@ThreadSafe
@Repository
public class Sql2oUserRepository implements UserRepository {

    private final Sql2o sql2o;

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public User save(User user) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                            """
                                    INSERT INTO users (full_name, email, password)
                                    VALUES (:full_name, :email, :password)
                                    """, true)
                    .addParameter("email", user.getEmail())
                    .addParameter("full_name", user.getFullName())
                    .addParameter("password", user.getPassword());
            Long generatedId = query.executeUpdate().getKey(Long.class);
            user.setId(generatedId);
            return user;
        } catch (Exception e) {
           throw new RuntimeException("Пользователь с таим email уже существует", e);
        }
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                            "SELECT * FROM users WHERE email = :email AND password = :password")
                    .addParameter("email", email)
                    .addParameter("password", password);
            var user = query.setColumnMappings(User.COLUMN_MAPPING)
                    .executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }
}
