package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Address;
import dev.carloscastano.get.entities.AddressType;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IAddressTypeService {

    //Métodos GET
    List<AddressType> findAll();
    Optional<AddressType> findById(Long id);
    Optional<AddressType> findByNombreTipoDireccion(String nombreTipoDireccion);
    List<Address> findAddressesByAddressTypeId(Long typeId);

    //Métodos POST
    AddressType save(AddressType addressType);

    //Métodos PATCH
    AddressType partialUpdate(AddressType addressType, Map<String, Object> updates);

    //Métodos DELETE
    void deleteById(Long id);

}