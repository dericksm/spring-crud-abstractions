package com.derick.services;

import com.derick.entities.Address;
import com.derick.entities.City;
import com.derick.entities.Client;
import com.derick.entities.Order;
import com.derick.entities.dto.ClientDTO;
import com.derick.entities.dto.ClientNewDTO;
import com.derick.entities.enums.ClientRole;
import com.derick.entities.enums.ClientType;
import com.derick.repositories.AddressRepository;
import com.derick.repositories.CityRepository;
import com.derick.repositories.ClientRepository;
import com.derick.security.UserDetailsImpl;
import com.derick.services.execeptions.AuthorizationException;
import com.derick.services.execeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ClientService extends AbstractService<Client, ClientDTO> {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public JpaRepository<Client, Integer> getRepository() {
        return clientRepository;
    }

    @Transactional
    public Client fromDTO(ClientNewDTO clientNewDTO) {
        Client client = new Client(null, clientNewDTO.getName(), clientNewDTO.getEmail(), clientNewDTO.getIdentifier(), ClientType.toEnum(clientNewDTO.getClientType()), bCryptPasswordEncoder.encode(clientNewDTO.getPassword()));
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

    @Override
    public Client findById(Integer id) {
        UserDetailsImpl user = UserService.authenticatedUser();
        if(user == null || !user.hasRole(ClientRole.ADMIN) && !id.equals(user.getId())){
            throw new AuthorizationException("Forbidden access!");
        }
        Client client = getRepository().findById(id).orElseThrow(() -> new ObjectNotFoundException("Entity not found."));
        return client;
    }
}

