package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.CartItems;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartItemsRepository extends CrudRepository<CartItems, Long> {
    List<CartItems> findByCarrito_IdCarrito(Long idCarrito);
}