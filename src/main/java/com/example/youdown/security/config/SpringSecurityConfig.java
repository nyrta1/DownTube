package com.example.youdown.security.config;

import com.example.youdown.security.settings.SecuritySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource())
                )
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(auth -> auth
                        // allow to system get login and register requests
                        .requestMatchers(HttpMethod.POST, SecuritySettings.LOGIN_REGISTER_REQUEST_LIST).permitAll()
                        // allow to user get json data
                        .requestMatchers(HttpMethod.GET, SecuritySettings.YOUTUBE_PARSED_JSON_DATA_SENDER).permitAll()
                        // allow to download media files for users
                        .requestMatchers(HttpMethod.GET, SecuritySettings.YOUTUBE_MEDIA_FILE_DOWNLOADER_API).permitAll()
                        // allow to show error pages
                        .requestMatchers(HttpMethod.GET, SecuritySettings.ALLOW_ERROR_PAGES_LIST).permitAll()

                        .anyRequest().authenticated()
                );

        return http.build();
    }

    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

