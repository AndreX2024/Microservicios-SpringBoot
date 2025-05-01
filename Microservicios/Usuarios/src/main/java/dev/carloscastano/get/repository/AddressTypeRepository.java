package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.AddressType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressTypeRepository extends CrudRepository<AddressType, Long> {

    Optional<AddressType> findByNombreTipoDireccion(String nombreTipoDireccion);

}
