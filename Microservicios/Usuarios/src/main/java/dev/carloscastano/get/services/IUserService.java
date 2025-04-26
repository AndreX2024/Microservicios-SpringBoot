package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Role;
import dev.carloscastano.get.entities.User;
import dev.carloscastano.get.entities.Address;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAll();
    Optional<User> getUserByDocumento(Long documento);
    Optional<List<Address>> getUserAddresses(Long id);
    List<Role> getRoles();
    User createUser(User user);

    Address addAddressUser(Long documentoUsuario, Address direccion);

}
