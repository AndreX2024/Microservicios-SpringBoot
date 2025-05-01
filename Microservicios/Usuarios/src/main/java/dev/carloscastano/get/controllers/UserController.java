package dev.carloscastano.get.controllers;


import dev.carloscastano.get.entities.Address;
import dev.carloscastano.get.entities.User;
import dev.carloscastano.get.repository.AddressRepository;
import dev.carloscastano.get.repository.AddressTypeRepository;
import dev.carloscastano.get.repository.UserRepository;
import dev.carloscastano.get.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {


    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressTypeRepository addressTypeRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        return user.map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<User> getUserByDocumento(@PathVariable Long documento) {
        Optional<User> user = userService.findByDocumento(documento);
        return user.map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{userId}/addresses")
    public ResponseEntity<List<Address>> getUserAddresses(@PathVariable Long userId) {
        List<Address> addresses = userService.findAddressesByUserId(userId);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    //Métodos POST
    @PostMapping
    public ResponseEntity<User> createUser(@Validated @RequestBody User user) {
        User newUser = userService.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    //Métodos PUT
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Validated @RequestBody User updatedUser) {
        Optional<User> existingUser = userService.findById(id);
        if (existingUser.isPresent()) {
            updatedUser.setIdUsuario(id);
            User savedUser = userService.save(updatedUser);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Métodos PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<User> partialUpdateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<User> existingUser = userService.findById(id);
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            User updatedUser = userService.partialUpdate(userToUpdate, updates);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Métodos DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
