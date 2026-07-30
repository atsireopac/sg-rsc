package br.gov.ife.sgrsc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/health").permitAll()

                        .requestMatchers(
                                "/error",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        .requestMatchers(
                                "/api/solicitacoes",
                                "/api/solicitacoes/**",
                                "/api/servidores",
                                "/api/servidores/**",
                                "/api/niveis-rsc",
                                "/api/niveis-rsc/**",
                                "/api/tipos-documento",
                                "/api/tipos-documento/**",
                                "/api/documentos",
                                "/api/documentos/**",
                                "/api/legislacoes",
                                "/api/legislacoes/**",
                                "/api/requisitos",
                                "/api/requisitos/**",
                                "/api/criterios",
                                "/api/criterios/**",
                                "/api/atividades",
                                "/api/atividades/**",
                                "/api/status-avaliacoes",
                                "/api/status-avaliacoes/**"
                        ).permitAll()

                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(Customizer.withDefaults())
                )
                .build();
    }
}