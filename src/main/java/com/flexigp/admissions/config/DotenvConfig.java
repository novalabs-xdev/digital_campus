package com.flexigp.admissions.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure()
                .directory("./")  // Cherche dans le répertoire racine
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();
    }
}
