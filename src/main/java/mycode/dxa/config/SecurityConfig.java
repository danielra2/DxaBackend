package mycode.dxa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf(csrf -> csrf.disable())


                .authorizeHttpRequests(auth -> auth
                        // a. Resursele statice și endpoint-urile de Auth sunt publice
                        .requestMatchers("/api/auth/**", "/css/**", "/js/**", "/images/**").permitAll()

                        // b. Endpoint-urile de Admin necesită rolul ADMIN
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // c. Orice altceva necesită autentificare
                        .anyRequest().authenticated()
                )

                // Pentru început folosim Basic Auth (pentru testare în Postman)
                // Mai târziu vom înlocui asta cu JWT Filter
                .httpBasic(basic -> {});

        return http.build();
    }
}