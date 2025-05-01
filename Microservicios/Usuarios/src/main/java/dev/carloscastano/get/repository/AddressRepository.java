package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {

    List<Address> findByUsuario_IdUsuario(Long userId);
    List<Address> findByTipoDireccion_IdTipoDireccion(Long addressTypeId);

}
