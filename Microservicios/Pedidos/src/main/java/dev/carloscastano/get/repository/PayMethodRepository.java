package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.PayMethod;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PayMethodRepository extends CrudRepository<PayMethod, Long> {
    Optional<PayMethod> findByMetodo(String metodo);
}