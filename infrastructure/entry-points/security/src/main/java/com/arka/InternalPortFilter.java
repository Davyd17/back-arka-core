package com.arka;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * A security filter that ensures internal reporting endpoints can only be accessed
 * through the designated internal network port.
 */
@Component
public class InternalPortFilter extends OncePerRequestFilter {

    @Value("${internal.server.port}")
    private int internalPort;

    /**
     * Evaluates incoming requests to enforce port-based access control on internal endpoints.
     * <p>
     * If the request URI targets the {@code api/v1/reports/internal} path but arrived on a port
     * other than the configured {@code internalPort}, this method short-circuits the filter chain
     * and returns an HTTP 403 Forbidden status. Otherwise, execution proceeds normally.
     * </p>
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        if ((request.getRequestURI().startsWith("/api/v1/reports/internal") ||
                request.getRequestURI().startsWith("/api/v1/internal"))
                && request.getLocalPort() != internalPort){

            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
