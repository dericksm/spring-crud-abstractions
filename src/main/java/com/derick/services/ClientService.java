package com.derick.services;

import com.derick.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends AbstractService<Client> {
    public ClientService(JpaRepository<Client, Integer> repository) {
        super(repository);
    }
}
