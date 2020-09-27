package com.derick.services;

import com.derick.entities.Category;
import com.derick.repositories.CategoryRepository;
import com.derick.services.execeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

public abstract class AbstractService<T> {

    private JpaRepository<T, Integer> repository;

    public AbstractService(JpaRepository<T, Integer> repository) {
        this.repository = repository;
    }

    public T findById(Integer id) {
        T obj = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Entity not found."));
        return obj;
    }
}
