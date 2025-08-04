package com.pluma.pluma.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditoriaConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // Retorna o nome do usuário atual.
        // Para este exemplo, estamos retornando um valor fixo.
        // Em uma aplicação real, você obteria o usuário do contexto de segurança.
        return () -> Optional.of("Sistema");
    }
}
