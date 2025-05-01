package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Address;
import dev.carloscastano.get.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    //Métodos GET
    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        return new ResponseEntity<>(addressService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        Optional<Address> address = addressService.findById(id);
        return address.map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Address>> getAddressesByUserId(@PathVariable Long userId) {
        List<Address> addresses = addressService.findByUserId(userId);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/type/{typeId}")
    public ResponseEntity<List<Address>> getAddressesByTypeId(@PathVariable Long typeId) {
        List<Address> addresses = addressService.findByAddressTypeId(typeId);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    //Métodos POST
    @PostMapping
    public ResponseEntity<Address> createAddress(@Validated @RequestBody Address address) {
        Address newAddress = addressService.save(address);
        return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
    }

    //Métodos PUT
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @Validated @RequestBody Address updatedAddress) {
        Optional<Address> existingAddress = addressService.findById(id);
        if (existingAddress.isPresent()) {
            updatedAddress.setIdDireccion(id);
            Address savedAddress = addressService.save(updatedAddress);
            return new ResponseEntity<>(savedAddress, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Métodos PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<Address> partialUpdateAddress(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Address> existingAddress = addressService.findById(id);
        if (existingAddress.isPresent()) {
            Address addressToUpdate = existingAddress.get();
            Address updatedAddress = addressService.partialUpdate(addressToUpdate, updates);
            return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Métodos DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        if (addressService.findById(id).isPresent()) {
            addressService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}