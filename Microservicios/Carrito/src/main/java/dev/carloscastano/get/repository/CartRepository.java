package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
    Optional<Cart> findByIdUsuario(Long idUsuario);
}