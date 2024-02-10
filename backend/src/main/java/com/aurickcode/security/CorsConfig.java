package com.aurickcode.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    
    @Value("#{'${cors.allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;
    @Value("#{'${cors.allowed-methods}'.split(',')}")
    private List<String> allowedMethods;
    @Value("#{'${cors.allowed-headers}'.split(',')}")
    private List<String> allowedHeaders;
    @Value("#{'${cors.exposed-headers}'.split(',')}")
    private List<String> exposedHeaders;

    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    //     // registry.addMapping("/**")
    //     //         .allowedOrigins("http://localhost:8080")
    //     //         .allowedMethods("GET", "POST", "PUT", "DELETE");

    //     CorsRegistration corsRegistration = registry.addMapping("/api/**");
    //     // allowedOrigins.forEach(origin -> corsRegistration.allowedOrigins(origin));
    //     // allowedMethods.forEach(origin -> corsRegistration.allowedMethods(origin));
    //     // allowedHeaders.forEach(origin -> corsRegistration.allowedHeaders(origin));
    //     allowedOrigins.forEach(corsRegistration::allowedOrigins);
    //     allowedMethods.forEach(corsRegistration::allowedMethods);
    //     allowedHeaders.forEach(corsRegistration::allowedHeaders);
    // }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(allowedMethods);
        // configuration.addAllowedHeader(HttpHeaders.AUTHORIZATION);
        configuration.setAllowedHeaders(allowedHeaders);
        configuration.setExposedHeaders(exposedHeaders);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
