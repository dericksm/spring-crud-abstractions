package com.derick.controllers;

import com.derick.entities.Category;
import com.derick.entities.Client;
import com.derick.services.AbstractService;
import com.derick.services.CategoryService;
import com.derick.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clients")
public class ClientController extends AbstractController<Client>{

    @Autowired
    public ClientController(ClientService service) {
        super(service);
    }
}
