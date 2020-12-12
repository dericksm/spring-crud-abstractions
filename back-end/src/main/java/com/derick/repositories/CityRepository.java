package com.derick.repositories;

import com.derick.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    @Transactional(readOnly = true)
    public List<City> findByStateIdOrderByName(Integer stateId);
}
