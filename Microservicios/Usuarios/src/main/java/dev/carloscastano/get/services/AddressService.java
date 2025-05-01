package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Address;
import dev.carloscastano.get.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AddressService implements IAddressService {

    @Autowired
    private AddressRepository addressRepository;

    //Métodos GET
    @Override
    public List<Address> findAll() {
        return (List<Address>) addressRepository.findAll();
    }

    @Override
    public Optional<Address> findById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public List<Address> findByUserId(Long userId) {
        return addressRepository.findByUsuario_IdUsuario(userId);
    }

    @Override
    public List<Address> findByAddressTypeId(Long addressTypeId) {
        return addressRepository.findByTipoDireccion_IdTipoDireccion(addressTypeId);
    }

    //Métodos POST
    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    //Métodos PATCH
    @Override
    public Address partialUpdate(Address address, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Address.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, address, value);
            }
        });
        return addressRepository.save(address);
    }

    //Métodos DELETE
    @Override
    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }

}