package com.arka.config;

import com.arka.InternalPortFilter;
import com.arka.JwtAuthenticationFilter;
import com.arka.SecurityExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final InternalPortFilter internalPortFilter;
    private final SecurityExceptionHandler securityExceptionHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsConfigurer ->
                        corsConfigurer.configurationSource(corsConfig.corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html").permitAll()
                        .requestMatchers("/actuator/health").permitAll()

                        //PUBLIC BROWSING
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/products/**",
                                "/api/v1/product-categories/**").permitAll()

                        //PUBLIC INTERNAL
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/reports/internal/**",
                                "/api/v1/internal/contacts").permitAll()

                        //ADMIN OPERATIONS
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/suppliers/categories/**",
                                "/api/v1/reports/**",
                                "/api/v1/shopping-carts/abandoned").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/products",
                                "/api/v1/suppliers",
                                "/api/v1/inventory-movements",
                                "/api/v1/shipping-details").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,
                                "/api/v1/orders/{orderId}/status").hasRole("ADMIN")

                        //USER / CUSTOMER
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/orders",
                                "/api/v1/shopping-carts/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH,
                                "/api/v1/orders").hasAnyRole("USER", "ADMIN")

                        .anyRequest().authenticated())

                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(securityExceptionHandler)
                        .authenticationEntryPoint(securityExceptionHandler))

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(internalPortFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
