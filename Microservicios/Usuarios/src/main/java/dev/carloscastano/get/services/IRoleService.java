package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Role;
import dev.carloscastano.get.entities.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IRoleService {
    //Métodos GET
    List<Role> findAll();
    Optional<Role> findById(Long id);
    Optional<Role> findByNombreRol(String nombreRol);
    List<User> findUsersByRoleId(Long roleId);

    //Métodos POST
    Role save(Role role);

    //Métodos PATCH
    Role partialUpdate(Role role, Map<String, Object> updates);

    //Métodos DELETE
    void deleteById(Long id);
}