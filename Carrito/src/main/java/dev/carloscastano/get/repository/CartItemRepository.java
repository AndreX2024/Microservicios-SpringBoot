package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.CartItems;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends CrudRepository<CartItems, Long> {
}
