package com.derick.services.validation;

import com.derick.controllers.exceptions.FieldMessage;
import com.derick.entities.Client;
import com.derick.entities.dto.ClientNewDTO;
import com.derick.entities.enums.ClientType;
import com.derick.repositories.ClientRepository;
import com.derick.services.utils.BrUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {

    @Autowired
    private ClientRepository repository;

    @Override
    public void initialize(ClientInsert ann) {
    }

    @Override
    public boolean isValid(ClientNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if(objDto.getClientType().equals(ClientType.PERSON.getValue()) && !BrUtils.isValidCPF(objDto.getIdentifier())) {
            list.add(new FieldMessage("Identifier", "Invalid identifier"));
        } else if(objDto.getClientType().equals(ClientType.COMPANY.getValue())&& !BrUtils.isValidCNPJ(objDto.getIdentifier())) {
            list.add(new FieldMessage("Identifier", "Invalid identifier"));
        }

        Client client = repository.findByEmail(objDto.getEmail());
        if(client != null) {
            list.add(new FieldMessage("Email", "E-mail already in use"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}