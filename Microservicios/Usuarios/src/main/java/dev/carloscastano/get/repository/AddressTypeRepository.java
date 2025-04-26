package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.Address;
import dev.carloscastano.get.entities.AddressType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressTypeRepository extends CrudRepository<AddressType, Long> {
}
