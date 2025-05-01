package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Address;
import dev.carloscastano.get.entities.AddressType;
import dev.carloscastano.get.services.IAddressTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/address-types")
public class AddressTypeController {

    @Autowired
    private IAddressTypeService addressTypeService;

    // Métodos GET
    @GetMapping
    public ResponseEntity<List<AddressType>> getAllAddressTypes() {
        return new ResponseEntity<>(addressTypeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressType> getAddressTypeById(@PathVariable Long id) {
        Optional<AddressType> addressType = addressTypeService.findById(id);
        return addressType.map(at -> new ResponseEntity<>(at, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<AddressType> getAddressTypeByNombre(@PathVariable String nombre) {
        Optional<AddressType> addressType = addressTypeService.findByNombreTipoDireccion(nombre);
        return addressType.map(at -> new ResponseEntity<>(at, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{typeId}/addresses")
    public ResponseEntity<List<Address>> getAddressesByAddressTypeId(@PathVariable Long typeId) {
        List<Address> addresses = addressTypeService.findAddressesByAddressTypeId(typeId);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    //Métodos POST
    @PostMapping
    public ResponseEntity<AddressType> createAddressType(@Validated @RequestBody AddressType addressType) {
        AddressType newAddressType = addressTypeService.save(addressType);
        return new ResponseEntity<>(newAddressType, HttpStatus.CREATED);
    }

    //Métodos PUT
    @PutMapping("/{id}")
    public ResponseEntity<AddressType> updateAddressType(@PathVariable Long id, @Validated @RequestBody AddressType updatedAddressType) {
        Optional<AddressType> existingAddressType = addressTypeService.findById(id);
        if (existingAddressType.isPresent()) {
            updatedAddressType.setIdTipoDireccion(id);
            AddressType savedAddressType = addressTypeService.save(updatedAddressType);
            return new ResponseEntity<>(savedAddressType, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Métodos PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<AddressType> partialUpdateAddressType(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<AddressType> existingAddressType = addressTypeService.findById(id);
        if (existingAddressType.isPresent()) {
            AddressType addressTypeToUpdate = existingAddressType.get();
            AddressType updatedAddressType = addressTypeService.partialUpdate(addressTypeToUpdate, updates);
            return new ResponseEntity<>(updatedAddressType, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Métodos DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddressType(@PathVariable Long id) {
        if (addressTypeService.findById(id).isPresent()) {
            addressTypeService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}