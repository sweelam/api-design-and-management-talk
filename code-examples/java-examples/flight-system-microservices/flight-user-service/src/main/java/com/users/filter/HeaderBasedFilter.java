package com.users.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

public class HeaderBasedFilter extends GenericFilterBean {

    private final String ADMIN_USER = "ADMIN_USER";

    // Allow admin to access with header & without credentials
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var request = (HttpServletRequest) servletRequest;
        var response = (HttpServletResponse) servletResponse;

        String header = request.getHeader("user-role");
        if (header == null || header.isEmpty()) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (!ADMIN_USER.equals(header)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-type", TEXT_PLAIN_VALUE + ";charset=utf-8");
            response.getWriter().println("user access is not allowed ⛔️");
            return;
        }

        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        emptyContext.setAuthentication(new ServiceAuth());
        SecurityContextHolder.setContext(emptyContext);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
