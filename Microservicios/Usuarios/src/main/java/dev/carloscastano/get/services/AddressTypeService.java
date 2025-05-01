package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Address;
import dev.carloscastano.get.entities.AddressType;
import dev.carloscastano.get.repository.AddressRepository;
import dev.carloscastano.get.repository.AddressTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AddressTypeService implements IAddressTypeService {

    @Autowired
    private AddressTypeRepository addressTypeRepository;

    @Autowired
    private AddressRepository addressRepository;

    //Métodos GET
    @Override
    public List<AddressType> findAll() {
        return (List<AddressType>) addressTypeRepository.findAll();
    }

    @Override
    public Optional<AddressType> findById(Long id) {
        return addressTypeRepository.findById(id);
    }

    @Override
    public Optional<AddressType> findByNombreTipoDireccion(String nombreTipoDireccion) {
        return addressTypeRepository.findByNombreTipoDireccion(nombreTipoDireccion);
    }

    @Override
    public List<Address> findAddressesByAddressTypeId(Long typeId) {
        return addressRepository.findByTipoDireccion_IdTipoDireccion(typeId);
    }

    //Métodos POST
    @Override
    public AddressType save(AddressType addressType) {
        return addressTypeRepository.save(addressType);
    }

    //Métodos PATCH
    @Override
    public AddressType partialUpdate(AddressType addressType, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(AddressType.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, addressType, value);
            }
        });
        return addressTypeRepository.save(addressType);
    }

    //Métodos DELETE
    @Override
    public void deleteById(Long id) {
        addressTypeRepository.deleteById(id);
    }

}