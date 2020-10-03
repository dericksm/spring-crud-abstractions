package com.derick.controllers;

import com.derick.entities.Client;
import com.derick.entities.dto.ClientDTO;
import com.derick.entities.dto.ClientNewDTO;
import com.derick.services.AbstractService;
import com.derick.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/clients")
public class ClientController extends AbstractController<Client, ClientDTO> {

    @Autowired
    private ClientService clientService;

    @Override
    public AbstractService<Client, ClientDTO> getService() {
        return clientService;
    }

    @PostMapping("/newClient")
    public ResponseEntity<Void> insertNewClient(@Valid @RequestBody ClientNewDTO clientNewDTO) {
        Client client = clientService.fromDTO(clientNewDTO);
        client = clientService.insertNewClient(client);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
