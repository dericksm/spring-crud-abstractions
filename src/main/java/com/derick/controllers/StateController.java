package com.derick.controllers;

import com.derick.entities.State;
import com.derick.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/states")
public class StateController extends AbstractController<State> {

    @Autowired
    public StateController(StateService service) {
        super(service);
    }
}
