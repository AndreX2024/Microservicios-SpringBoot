package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByNombreRol(String nombreRol);

}
