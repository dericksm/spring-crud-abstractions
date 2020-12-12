package com.derick.entities;

import com.derick.entities.enums.ClientRole;
import com.derick.entities.enums.ClientType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.management.relation.Role;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Client implements Serializable, BaseEntity {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String email;

    private String identifier;

    private Integer clientType;

    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "phones")
    private Set<String> phones = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles")
    private Set<Integer> roles = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<Order> orders = new ArrayList<>();

    public Client() {
        addRole(ClientRole.CLIENT);
    }

    public Client(Integer id, String name, String email, String identifier, ClientType clientType, String password) {
        addRole(ClientRole.CLIENT);
        this.id = id;
        this.name = name;
        this.email = email;
        this.identifier = identifier;
        this.clientType = (clientType == null) ? null : clientType.getValue();
        this.password = password;
    }

    public Client(Integer id, String name, String email) {
        addRole(ClientRole.CLIENT);
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ClientType getClientType() {
        return ClientType.toEnum(this.clientType);
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType.getValue();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<ClientRole> getRoles() {
        return roles.stream().map(x -> ClientRole.toEnum(x)).collect(Collectors.toSet());
    }

    public void addRole(ClientRole role) {
        this.roles.add(role.getValue());
    };

    public List<Address> getAddresses() {
        return addresses;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return Objects.equals(getId(), client.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
