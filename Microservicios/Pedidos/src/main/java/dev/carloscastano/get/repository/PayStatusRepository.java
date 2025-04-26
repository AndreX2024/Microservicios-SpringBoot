package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.Order;
import dev.carloscastano.get.entities.PayStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayStatusRepository extends CrudRepository<PayStatus, Long> {
}