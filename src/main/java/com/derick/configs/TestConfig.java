package com.derick.configs;

import com.derick.services.DbService;
import com.derick.services.EmailService;
import com.derick.services.MockMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private DbService dbService;

    @Bean
    public EmailService emailService(){
        return new MockMailService();
    }

    @Override
    public void run(String... args) throws Exception {
        dbService.instantiateDatabase();
    }
}
