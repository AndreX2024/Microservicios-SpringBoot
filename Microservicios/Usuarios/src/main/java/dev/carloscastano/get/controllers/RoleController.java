package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Role;
import dev.carloscastano.get.entities.User;
import dev.carloscastano.get.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    //Métodos GET
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleService.findById(id);
        return role.map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Role> getRoleByNombre(@PathVariable String nombre) {
        Optional<Role> role = roleService.findByNombreRol(nombre);
        return role.map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{roleId}/users")
    public ResponseEntity<List<User>> getUsersByRoleId(@PathVariable Long roleId) {
        List<User> users = roleService.findUsersByRoleId(roleId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //Métodos POST
    @PostMapping
    public ResponseEntity<Role> createRole(@Validated @RequestBody Role role) {
        Role newRole = roleService.save(role);
        return new ResponseEntity<>(newRole, HttpStatus.CREATED);
    }

    //Métodos PUT
    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @Validated @RequestBody Role updatedRole) {
        Optional<Role> existingRole = roleService.findById(id);
        if (existingRole.isPresent()) {
            updatedRole.setIdRol(id);
            Role savedRole = roleService.save(updatedRole);
            return new ResponseEntity<>(savedRole, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Métodos PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<Role> partialUpdateRole(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Role> existingRole = roleService.findById(id);
        if (existingRole.isPresent()) {
            Role roleToUpdate = existingRole.get();
            Role updatedRole = roleService.partialUpdate(roleToUpdate, updates);
            return new ResponseEntity<>(updatedRole, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Métodos DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        if (roleService.findById(id).isPresent()) {
            roleService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}