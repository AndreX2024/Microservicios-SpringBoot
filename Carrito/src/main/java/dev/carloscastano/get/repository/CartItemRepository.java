package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends CrudRepository<Cart, Long> {
}
