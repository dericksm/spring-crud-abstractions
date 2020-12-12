package com.derick.configs;

import com.derick.services.DbService;
import com.derick.services.EmailService;
import com.derick.services.MockMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private DbService dbService;

    @Bean
    public EmailService emailService() {
        return new MockMailService();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }

    @Override
    public void run(String... args) throws Exception {
        dbService.instantiateDatabase();
    }
}
