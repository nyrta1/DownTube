package com.example.youdown.security.config;

import com.example.youdown.security.settings.SecuritySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .authorizeRequests(auth -> auth
                        // allow to user get json data
                        .requestMatchers(SecuritySettings.YOUTUBE_PARSED_JSON_DATA_SENDER).permitAll()
                        // allow to download media files for users
                        .requestMatchers(SecuritySettings.YOUTUBE_MEDIA_FILE_DOWNLOADER_API).permitAll()
                        // allow to show error pages
                        .requestMatchers(HttpMethod.GET, SecuritySettings.ALLOW_ERROR_PAGES_LIST).permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

