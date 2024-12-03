package ru.job4j.cinema.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ExtendWith(OutputCaptureExtension.class)
class SimpleUserServiceTest {

    @MockBean
    private UserRepository repository;
    @Autowired
    private SimpleUserService service;

    @Test
    void saveSuccess() {
        when(repository.save(argThat(it -> it.getFullName().equals("fullName"))))
                .thenReturn(
                        Optional.of(
                                User.builder()
                                        .fullName("fullName")
                                        .email("email")
                                        .build()
                        )
                );

        var result = service.save(User.builder().fullName("fullName").build());

        assertTrue(result.isPresent());
        assertEquals("fullName", result.get().getFullName());
    }

    @Test
    void findByEmailAndPassword() {
        when(repository.findByEmailAndPassword("email", "password"))
                .thenReturn(
                        Optional.of(
                                User.builder()
                                        .fullName("fullName")
                                        .email("email")
                                        .password("password")
                                        .build()
                        )
                );

        var result = service.findByEmailAndPassword("email", "password");

        assertTrue(result.isPresent());
        assertEquals("email", result.get().getEmail());
        assertEquals("password", result.get().getPassword());
    }

    @Test
    void testCapturedAspectLoggingWhenFoundUser(CapturedOutput output) {
        when(repository.findByEmailAndPassword("email", "password"))
                .thenReturn(
                        Optional.of(
                                User.builder()
                                        .fullName("fullName")
                                        .email("email")
                                        .password("password")
                                        .build()
                        )
                );

        assertDoesNotThrow(() -> service.findByEmailAndPassword("email", "password"));

        Assertions.assertThat(output)
                .contains("Before method: findByEmailAndPassword invoked")
                .contains("Email: email password: password")
                .contains("AfterReturning method findByEmailAndPassword invoked")
                .contains("Returning user name: fullName , email: email invoked")
                .contains("After method: findByEmailAndPassword invoked. This finally after block");
    }

    @Test
    void testCapturedAspectLoggingWhenNotFoundUser(CapturedOutput output) {
        when(repository.findByEmailAndPassword("email", "password"))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() -> service.findByEmailAndPassword("email", "password"));

        Assertions.assertThat(output)
                .contains("Before method: findByEmailAndPassword invoked")
                .contains("Email: email password: password")
                .contains("AfterReturning method findByEmailAndPassword invoked")
                .contains("Returning user is empty")
                .contains("After method: findByEmailAndPassword invoked. This finally after block");
    }

    @Test
    void testCapturedAspectLoggingWhenThrowing(CapturedOutput output) {
        when(repository.findByEmailAndPassword("email", "password"))
                .thenThrow(new RuntimeException("Connection failed"));

        assertThrows(Exception.class, () -> service.findByEmailAndPassword("email", "password"));

        Assertions.assertThat(output)
                .contains("Before method: findByEmailAndPassword invoked")
                .contains("Email: email password: password")
                .contains("AfterThrowing method: findByEmailAndPassword invoked")
                .contains("Found problems when finding user by Email: email and Password: password.")
                .contains("Exception: Connection failed")
                .contains("After method: findByEmailAndPassword invoked. This finally after block");
    }
}