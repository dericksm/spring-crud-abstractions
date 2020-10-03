package com.derick.controllers;

import com.derick.entities.BaseEntity;
import com.derick.entities.dto.BaseDTO;
import com.derick.services.AbstractService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public abstract class AbstractController<T extends BaseEntity, D extends BaseDTO> {

    public abstract AbstractService<T, D> getService();

    @GetMapping(value = "/{id}")
    public ResponseEntity<T> findById(@PathVariable Integer id){
        T obj = getService().findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<List<T>> findAll(){
        List<T> objList = getService().findAll();
        return ResponseEntity.ok().body(objList);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<D>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ){
        Page<T> objList = getService().findPage(page, size, orderBy, direction);
        Page<D> dtoList = objList.map(obj -> getService().convertToOrderDto(obj));
        return ResponseEntity.ok().body(dtoList);

    }

    @PostMapping
    public ResponseEntity<T> insert(@Valid @RequestBody D objDTO) {
        T obj = getService().fromDTO(objDTO);
        obj = getService().insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody D objDTO) {
        T obj = getService().fromDTO(objDTO);
        obj.setId(id);
        obj = getService().update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        getService().deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
