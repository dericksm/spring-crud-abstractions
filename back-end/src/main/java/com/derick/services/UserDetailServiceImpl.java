package com.derick.services;

import com.derick.entities.Client;
import com.derick.repositories.ClientRepository;
import com.derick.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(email);
        if(client == null) {
            throw new UsernameNotFoundException(email);
        }
        return new UserDetailsImpl(client.getId(), client.getEmail(), client.getPassword(), client.getRoles());
    }
}
