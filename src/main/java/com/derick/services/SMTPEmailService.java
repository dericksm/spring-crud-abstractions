package com.derick.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

public class SMTPEmailService extends AbstractMailService {

    private static final Logger logger = LoggerFactory.getLogger(SMTPEmailService.class);

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        logger.info("Sending new email!");
        mailSender.send(msg);
        logger.info("E-mail sent!");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        logger.info("Sending new email!");
        javaMailSender.send(msg);
        logger.info("E-mail sent!");
    }
}
