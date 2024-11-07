package com.se100.clinic_management.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  public WebConfig() {
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll() // Cho phép tất cả các request mà không cần xác thực
        )
        .csrf(csrf -> csrf.disable()); // Tắt CSRF

    return http.build();
  }

  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins(new String[] { "http://localhost:8080" })
        .allowedMethods(new String[] { "GET", "POST", "PUT", "DELETE" }).allowedHeaders(new String[] { "*" })
        .allowCredentials(true);
  }
}
