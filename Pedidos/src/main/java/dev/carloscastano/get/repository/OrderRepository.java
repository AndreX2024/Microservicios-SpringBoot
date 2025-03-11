package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.Order;
import dev.carloscastano.get.entities.Pay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByIdUsuario(Long idUsuario);
}
