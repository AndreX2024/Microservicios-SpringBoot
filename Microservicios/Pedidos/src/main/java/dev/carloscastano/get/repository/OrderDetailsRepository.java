package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.OrderDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends CrudRepository<OrderDetails, Long> {
    List<OrderDetails> findByPedido_IdPedido(Long pedidoId);
}