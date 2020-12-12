package com.derick.services;

import com.derick.entities.City;
import com.derick.entities.dto.CityDTO;
import com.derick.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService extends AbstractService<City, CityDTO> {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public JpaRepository<City, Integer> getRepository() {
        return cityRepository;
    }

    public List<City> findAllByStateId(Integer stateId){
        return cityRepository.findByStateIdOrderByName(stateId);
    }
}

