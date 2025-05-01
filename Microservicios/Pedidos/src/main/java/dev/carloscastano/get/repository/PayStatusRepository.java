package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.PayStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PayStatusRepository extends CrudRepository<PayStatus, Long> {
    Optional<PayStatus> findByEstado(String estado);
}