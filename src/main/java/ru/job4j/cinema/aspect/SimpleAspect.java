package ru.job4j.cinema.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import ru.job4j.cinema.model.User;

import java.util.Optional;

@Slf4j
@Aspect
@Component
public class SimpleAspect {

    @Pointcut(value = "@annotation(MyCustomAspectAnnotation) && args(email, password)", argNames = "email,password")
    public void pointcut(String email, String password) {}

    @Before(value = "pointcut(email, password)", argNames = "joinPoint,email,password")
    public void before(JoinPoint joinPoint, String email, String password) {
        log.info("Before method: {} invoked", joinPoint.getSignature().getName());
        log.info("Email: {} password: {}", email, password);
    }

    @After(value = "pointcut(email, password)", argNames = "joinPoint,email,password")
    public void after(JoinPoint joinPoint, String email, String password) {
        log.info("After method: {} invoked. This finally after block", joinPoint.getSignature().getName());
    }

    @AfterReturning(value = "pointcut(email, password)", returning = "result", argNames = "joinPoint,email,password,result")
    public void afterReturning(JoinPoint joinPoint, String email, String password, Optional<User> result) {
        log.info("AfterReturning method {} invoked", joinPoint.getSignature().getName());
        if (result.isPresent()) {
            var user = result.get();
            log.info("Returning user name: {} , email: {} invoked", user.getFullName(), user.getEmail());
        } else {
            log.info("Returning user is empty");
        }
    }

    @AfterThrowing(value = "pointcut(email, password)", throwing = "exception", argNames = "joinPoint,email,password,exception")
    public void afterThrowing(JoinPoint joinPoint, String email, String password, Exception exception) {
        log.info("AfterThrowing method: {} invoked", joinPoint.getSignature().getName());
        log.info("Found problems when finding user by Email: {} and Password: {}.", email, password);
        log.info("Exception: {}", exception.getMessage());
    }
}
