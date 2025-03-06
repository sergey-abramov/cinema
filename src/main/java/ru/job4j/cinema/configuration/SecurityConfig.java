package ru.job4j.cinema.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import ru.job4j.cinema.filter.SecurityUtil;

import javax.servlet.http.HttpServletResponse;

//@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions()
                .disable()
                .and()

                .authorizeRequests()
                .antMatchers(
                        "/common/**",
                        "/extjs/**"
                ).permitAll()

                .antMatchers(
                        "/info/**",
                        "/index.html"
                )

                .hasAnyRole()

                .regexMatchers("^/?$")
                .authenticated()

                .anyRequest()
                .denyAll()
                .and()

                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
                .and()

                .logout()
                .invalidateHttpSession(true)
                .logoutUrl("/logout")
                .and()

                .exceptionHandling()
                .authenticationEntryPoint((request, response, exception) -> {
                    if (SecurityUtil.isAcceptTextHtml(request)) {
                       response.sendRedirect("localhost:8080/login");
                    } else {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                })
                .accessDeniedHandler((request, response, exception) ->
                        response.sendError(HttpServletResponse.SC_FORBIDDEN)
                )
                .and()

                .csrf().disable();
    }
}