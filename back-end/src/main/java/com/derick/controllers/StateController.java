package com.derick.controllers;

import com.derick.entities.City;
import com.derick.entities.State;
import com.derick.entities.dto.CityDTO;
import com.derick.entities.dto.StateDTO;
import com.derick.services.CityService;
import com.derick.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/states")
public class StateController {

    @Autowired
    private StateService stateService;

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<StateDTO>> findAll() {
        List<State> stateList = stateService.findAll();
        List<StateDTO> stateDTOList = stateList.stream().map(state -> new StateDTO(state)).collect(Collectors.toList());
        return ResponseEntity.ok().body(stateDTOList);
    }

    @GetMapping(value = "/{stateId}/cities")
    public ResponseEntity<List<CityDTO>> findAllByStateId(@PathVariable Integer stateId) {
        List<City> cityList = cityService.findAllByStateId(stateId);
        List<CityDTO> cityDTOList = cityList.stream().map(city -> new CityDTO(city)).collect(Collectors.toList());
        return ResponseEntity.ok().body(cityDTOList);
    }
}
