package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Address;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IAddressService {

    //Métodos GET
    List<Address> findAll();
    Optional<Address> findById(Long id);
    List<Address> findByUserId(Long userId);
    List<Address> findByAddressTypeId(Long addressTypeId);

    //Métodos POST
    Address save(Address address);

    //Métodos PATCH
    Address partialUpdate(Address address, Map<String, Object> updates);

    //Métodos DELETE
    void deleteById(Long id);

}