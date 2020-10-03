package com.derick.services;

import com.derick.entities.BaseEntity;
import com.derick.entities.Category;
import com.derick.entities.dto.BaseDTO;
import com.derick.repositories.CategoryRepository;
import com.derick.services.execeptions.DataIntegrityException;
import com.derick.services.execeptions.ObjectNotFoundException;
import com.derick.utils.DTOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

public abstract class AbstractService<T extends BaseEntity, D extends BaseDTO> {

    @Autowired
    private ModelMapper modelMapper;

    protected final Class<D> dtoEntityType;
    protected final Class<T> entityType;

    public abstract JpaRepository<T, Integer> getRepository();

    public AbstractService() {
        this.entityType =  (Class<T>) GenericTypeResolver.resolveTypeArguments(getClass(), AbstractService.class)[0];
        this.dtoEntityType =  (Class<D>) GenericTypeResolver.resolveTypeArguments(getClass(), AbstractService.class)[1];
    }

    public D convertToOrderDto(T obj) {
        D objDTO = modelMapper.map(obj, dtoEntityType);
        return objDTO;
    }

    public T fromDTO(D objDTO) {
        T obj = modelMapper.map(objDTO, entityType);
        return obj;
    }

    public T findById(Integer id) {
        T obj = getRepository().findById(id).orElseThrow(() -> new ObjectNotFoundException("Entity not found."));
        return obj;
    }

    @Transactional
    public T insert(T obj) {
        return getRepository().save(obj);
    }

    public T update(T obj) {
        T newObj = findById(obj.getId());
        BeanUtils.copyProperties(obj, newObj, DTOUtils.getNullPropertyNames(obj));
        return getRepository().save(newObj);
    }

    public void deleteById(Integer id) {
        findById(id);
        try {
            getRepository().deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityException("Couldn't delete entity!");
        }
    }

    public List<T> findAll() {
        return getRepository().findAll();
    }

    public Page<T> findPage(Integer page, Integer size, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        return getRepository().findAll(pageRequest);
    }
}
