package com.derick.controllers;

import com.derick.entities.Client;
import com.derick.entities.dto.ClientDTO;
import com.derick.entities.dto.ClientNewDTO;
import com.derick.services.AbstractService;
import com.derick.services.ClientService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<Client>> findAll() {
        return super.findAll();
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Page<ClientDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        return super.findPage(page, size, orderBy, direction);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    @PostMapping("/picture")
    public ResponseEntity<Void> uploadProfilePicture(@RequestParam(value = "file") MultipartFile multipartFile) {
        URI uri = clientService.uploadProfilePicture(multipartFile);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value=  "/email")
    public ResponseEntity<Client> findByEmail(@RequestParam(value = "email") String email){
        Client client = clientService.findByEmail(email);
        return ResponseEntity.ok().body(client);
    }
}
