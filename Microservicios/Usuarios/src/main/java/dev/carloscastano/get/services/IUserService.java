package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Role;
import dev.carloscastano.get.entities.User;
import dev.carloscastano.get.entities.Address;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserService {

    //Métodos GET
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByDocumento(Long documento);
    List<Address> findAddressesByUserId(Long userId);

    //Métodos POST
    User save(User user);

    //Métodos PATCH
    User partialUpdate(User user, Map<String, Object> updates);

    //Métodos DELETE
    void deleteById(Long id);

}
