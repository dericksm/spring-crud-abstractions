package com.derick.services;

import com.derick.entities.Client;
import com.derick.entities.Order;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Order order);

    void sendEmail(SimpleMailMessage msg);

    void sendOrderConfirmationHtmlEmail(Order order);

    void sendHtmlEmail(MimeMessage msg);

    void sendNewPasswordEmail(Client client, String newPassword);
}
