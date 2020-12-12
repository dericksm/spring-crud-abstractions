package com.derick.services;

import com.derick.entities.Client;
import com.derick.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractMailService implements EmailService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(Order order) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromOrder(order);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromOrder(Order order) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(order.getClient().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Pedido confirmado! Código: " + order.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(order.toString());
        return sm;
    }

    protected MimeMessage prepareMimeMessageFromOrder(Order order) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(order.getClient().getEmail());
        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setSubject("Pedido confirmado! Código: " + order.getId());
        mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
        mimeMessageHelper.setText(order.toString());
        return mimeMessage;
    }

    protected String htmlFromTemplateOrder(Order order) {
        Context context = new Context();
        context.setVariable("order", order);
        return templateEngine.process("email/orderConfirmation", context);
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(Order order) {
        try {
            MimeMessage sm = prepareMimeMessageFromOrder(order);
            sendHtmlEmail(sm);
        } catch (MessagingException ex) {
            sendOrderConfirmationEmail(order);
        }
    }

    @Override
    public void sendNewPasswordEmail(Client client, String newPassword) {
        SimpleMailMessage sm = prepareNewPasswordEmail(client, newPassword);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(Client client, String newPassword) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(client.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de nova senha");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Nova senha " + newPassword);
        return sm;
    }
}
