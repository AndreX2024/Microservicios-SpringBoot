package dev.carloscastano.get.controllers;


import dev.carloscastano.get.entities.Address;
import dev.carloscastano.get.entities.AddressType;
import dev.carloscastano.get.entities.Role;
import dev.carloscastano.get.entities.User;
import dev.carloscastano.get.repository.AddressRepository;
import dev.carloscastano.get.repository.AddressTypeRepository;
import dev.carloscastano.get.repository.UserRepository;
import dev.carloscastano.get.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {


    @Autowired
    private IUserService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressTypeRepository addressTypeRepository;

    @GetMapping
    public List<User> getAll() {return service.getAll();}

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<User> getUserByDocumento(@PathVariable Long documento) {
        return ResponseEntity.of(service.getUserByDocumento(documento));
    }

    @GetMapping("/{id}/direcciones")
    public ResponseEntity<List<Address>> getUserAddresses(@PathVariable Long id) {
        return ResponseEntity.of(service.getUserAddresses(id));
    }

    // Nuevo endpoint para tipos de dirección
    @GetMapping("/address-types")
    public ResponseEntity<List<AddressType>> getAddressTypes() {
        List<AddressType> tipos = (List<AddressType>) addressTypeRepository.findAll();
        return ResponseEntity.ok(tipos);
    }

    // Nuevo endpoint para obtener dirección por ID
    @GetMapping("/addresses/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        Optional<Address> direccion = addressRepository.findById(id);
        return direccion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = service.getRoles();  // Asegúrate de tener un método en el servicio para obtener los roles
        return ResponseEntity.ok(roles);
    }


    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody User user) {
        service.createUser(user);
        return ResponseEntity.ok().body(Map.of("mensaje", "Usuario creado exitosamente"));
    }


    @PostMapping("/{documentoUsuario}/direcciones")
    public ResponseEntity<?> addAddress(@PathVariable Long documentoUsuario, @RequestBody Address direccion) {
        Address nuevaDireccion = service.addAddressUser(documentoUsuario, direccion);

        if (nuevaDireccion != null) {
            return ResponseEntity.ok(Collections.singletonMap("mensaje", "Dirección agregada correctamente"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "Usuario no encontrado"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            User existingUser = userData.get();

            // Actualizar todos los campos
            existingUser.setDocumento(user.getDocumento());
            existingUser.setNombre(user.getNombre());
            existingUser.setApellido(user.getApellido());
            existingUser.setEmail(user.getEmail());
            existingUser.setTelefono(user.getTelefono());
            existingUser.setContraseña(user.getContraseña());
            existingUser.setRol(user.getRol());

            // Guardar usuario actualizado
            return new ResponseEntity<>(userRepository.save(existingUser), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/address/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable("id") Long id, @RequestBody Address direccion) {
        Optional<Address> addressData = addressRepository.findById(id);
        if (addressData.isPresent()) {
            Address existingAddress = addressData.get();

            // Actualizar todos los campos
            existingAddress.setCalle(direccion.getCalle());
            existingAddress.setCiudad(direccion.getCiudad());
            existingAddress.setDepartamento(direccion.getDepartamento());
            existingAddress.setCodigo_postal(direccion.getCodigo_postal());
            existingAddress.setTipoDireccion(direccion.getTipoDireccion());

            // Guardar dirección actualizada
            return new ResponseEntity<>(addressRepository.save(existingAddress), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PatchMapping("update/{id}")
    public ResponseEntity<User> patchUser(@PathVariable("id") Long id, @RequestBody Map<String, Object> updates) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            User existingUser = userData.get();

            // Solo actualizamos los campos que están en el mapa `updates`
            if (updates.containsKey("documento")) {
                existingUser.setDocumento((Long) updates.get("documento"));
            }
            if (updates.containsKey("nombre")) {
                existingUser.setNombre((String) updates.get("nombre"));
            }
            if (updates.containsKey("apellido")) {
                existingUser.setApellido((String) updates.get("apellido"));
            }
            if (updates.containsKey("email")) {
                existingUser.setEmail((String) updates.get("email"));
            }
            if (updates.containsKey("telefono")) {
                existingUser.setTelefono((String) updates.get("telefono"));
            }
            if (updates.containsKey("contraseña")) {
                existingUser.setContraseña((String) updates.get("contraseña"));
            }
            if (updates.containsKey("rol")) {
                existingUser.setRol((Role) updates.get("rol"));
            }

            // Guardar usuario actualizado
            return new ResponseEntity<>(userRepository.save(existingUser), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PatchMapping("/update/address/{id}")
    public ResponseEntity<Address> patchAddress(@PathVariable("id") Long id, @RequestBody Map<String, Object> updates) {
        Optional<Address> addressData = addressRepository.findById(id);
        if (addressData.isPresent()) {
            Address existingAddress = addressData.get();

            // Solo actualizamos los campos que están en el mapa `updates`
            if (updates.containsKey("calle")) {
                existingAddress.setCalle((String) updates.get("calle"));
            }
            if (updates.containsKey("ciudad")) {
                existingAddress.setCiudad((String) updates.get("ciudad"));
            }
            if (updates.containsKey("departamento")) {
                existingAddress.setDepartamento((String) updates.get("departamento"));
            }
            if (updates.containsKey("codigo_postal")) {
                existingAddress.setCodigo_postal((String) updates.get("codigo_postal"));
            }
            if (updates.containsKey("tipoDireccion")) {
                existingAddress.setTipoDireccion((AddressType) updates.get("tipoDireccion"));
            }

            // Guardar dirección actualizada
            return new ResponseEntity<>(addressRepository.save(existingAddress), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
