package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Role;
import dev.carloscastano.get.entities.User;
import dev.carloscastano.get.repository.RoleRepository;
import dev.carloscastano.get.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    //Métodos GET
    @Override
    public List<Role> findAll() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> findByNombreRol(String nombreRol) {
        return roleRepository.findByNombreRol(nombreRol);
    }

    @Override
    public List<User> findUsersByRoleId(Long roleId) {
        Optional<Role> roleOptional = roleRepository.findById(roleId);
        return roleOptional.map(Role::getUsuarios).orElse(List.of());
    }

    //Métodos POST
    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    //Métodos PATCH
    @Override
    public Role partialUpdate(Role role, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Role.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, role, value);
            }
        });
        return roleRepository.save(role);
    }

    //Métodos DELETE
    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

}