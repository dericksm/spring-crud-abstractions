package com.derick.configs;

import com.derick.services.DbService;
import com.derick.services.EmailService;
import com.derick.services.SMTPEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig implements CommandLineRunner {

    @Autowired
    private DbService dbService;

    @Bean
    public EmailService emailService(){
        return new SMTPEmailService();
    }

    @Override
    public void run(String... args) throws Exception {
        dbService.instantiateDatabase();
    }
}
