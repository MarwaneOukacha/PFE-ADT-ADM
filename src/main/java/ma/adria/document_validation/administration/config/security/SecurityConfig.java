package ma.adria.document_validation.administration.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] PUBLIC_RESOURCES = {
            "/login/**",
            "/api-docs/**",
            "/swagger-ui/**",
            "/api/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize.antMatchers(PUBLIC_RESOURCES).permitAll()
                        .anyRequest().authenticated()
                );

        http.oauth2ResourceServer().jwt();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}
