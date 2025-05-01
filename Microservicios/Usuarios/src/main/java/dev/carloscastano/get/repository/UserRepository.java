package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    //Métodos GEt
    Optional<User> findByEmail(String email);
    Optional<User> findByDocumento(Long documento);

}
