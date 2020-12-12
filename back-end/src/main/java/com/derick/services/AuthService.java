package com.derick.services;

import com.derick.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Random random = new Random();

    public void sendNewPassword(String email){
        Client client = clientService.findByEmailWithNoAuth(email);
        String newPassword = newPassword();
        client.setPassword(bCryptPasswordEncoder.encode(newPassword));
        clientService.update(client);
        emailService.sendNewPasswordEmail(client, newPassword);
    }

    private String newPassword(){
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar(){
        int opt = random.nextInt(3);
        if(opt == 0) {
            return (char) (random.nextInt(10) + 48);
        } else if(opt == 1) {
            return (char) (random.nextInt(26) + 65);
        } else {
            return (char) (random.nextInt(26) + 97);
        }
    }
}
