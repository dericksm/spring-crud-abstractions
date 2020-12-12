package com.derick.services;

import com.derick.entities.State;
import com.derick.entities.dto.StateDTO;
import com.derick.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService extends AbstractService<State, StateDTO> {

    @Autowired
    private StateRepository stateRepository;

    @Override
    public JpaRepository<State, Integer> getRepository() {
        return stateRepository;
    }

    @Override
    public List<State> findAll() {
        return stateRepository.findAllByOrderByName();
    }
}

