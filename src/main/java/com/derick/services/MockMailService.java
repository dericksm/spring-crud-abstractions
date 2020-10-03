package com.derick.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public class MockMailService extends AbstractMailService {

    private static final Logger logger = LoggerFactory.getLogger(MockMailService.class);

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        logger.info("Fake Email");
        logger.info(msg.toString());
        logger.info("E-mail sent!");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        logger.info("Fake Email");
        logger.info(msg.toString());
        logger.info("E-mail sent!");
    }
}
