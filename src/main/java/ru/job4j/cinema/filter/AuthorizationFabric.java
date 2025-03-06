package ru.job4j.cinema.filter;

import javax.servlet.http.HttpFilter;

public class AuthorizationFabric {

    public HttpFilter getProdFilter() {
        return new AuthorizationFilter();
    }

    public HttpFilter getDevFilter() {
        return new AuthorizationDevFilter();
    }
}
