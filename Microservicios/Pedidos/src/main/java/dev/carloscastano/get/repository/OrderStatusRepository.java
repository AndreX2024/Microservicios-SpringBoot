package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.OrderStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends CrudRepository<OrderStatus, Long> {
    Optional<OrderStatus> findByEstado(String estado);
}