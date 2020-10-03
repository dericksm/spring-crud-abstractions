package com.derick.services;

import com.derick.entities.Address;
import com.derick.entities.Category;
import com.derick.entities.City;
import com.derick.entities.Client;
import com.derick.entities.dto.ClientDTO;
import com.derick.entities.dto.ClientNewDTO;
import com.derick.entities.enums.ClientType;
import com.derick.repositories.AddressRepository;
import com.derick.repositories.CategoryRepository;
import com.derick.repositories.CityRepository;
import com.derick.repositories.ClientRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ClientService extends AbstractService<Client, ClientDTO> {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public JpaRepository<Client, Integer> getRepository() {
        return clientRepository;
    }

    @Transactional
    public Client fromDTO(ClientNewDTO clientNewDTO) {
        Client client = new Client(null, clientNewDTO.getName(), clientNewDTO.getEmail(), clientNewDTO.getIdentifier(), ClientType.toEnum(clientNewDTO.getClientType()));
        City city = cityRepository.findById(clientNewDTO.getCityId()).orElse(null);
        Address address = new Address(null, clientNewDTO.getStreet(), clientNewDTO.getNumber(), clientNewDTO.getComplement(), clientNewDTO.getDistrict(), clientNewDTO.getZipCode(), city, client);
        client.getAddresses().add(address);
        client.getPhones().add(clientNewDTO.getPhone1());
        if(clientNewDTO.getPhone2() != null) client.getPhones().add(clientNewDTO.getPhone2());
        if(clientNewDTO.getPhone3() != null) client.getPhones().add(clientNewDTO.getPhone3());
        return client;
    }

    @Transactional
    public Client insertNewClient(Client client){
        client = clientRepository.save(client);
        addressRepository.saveAll(client.getAddresses());
        return client;
    }
}
