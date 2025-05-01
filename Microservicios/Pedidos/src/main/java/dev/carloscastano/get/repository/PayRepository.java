package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.Pay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayRepository extends CrudRepository<Pay, Long> {
    Optional<Pay> findByPedido_IdPedido(Long pedidoId);
    List<Pay> findByMetodoPago_IdMetodo(Long metodoId);
    List<Pay> findByEstadoPago_IdEstadoPago(Long estadoPagoId);
}