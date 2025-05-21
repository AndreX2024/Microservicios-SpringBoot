package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Address;
import dev.carloscastano.get.entities.User;
import dev.carloscastano.get.repository.AddressRepository;
import dev.carloscastano.get.repository.RoleRepository;
import dev.carloscastano.get.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByDocumento(Long documento) {
        return userRepository.findByDocumento(documento);
    }

    @Override
    public List<Address> findAddressesByUserId(Long userId) {
        return addressRepository.findByUsuario_IdUsuario(userId);
    }

    //Métodos POST
    @Override
    public User save(User user) {
        if (user.getContraseña() != null && !user.getContraseña().isEmpty()) {
            user.setContraseña(passwordEncoder.encode(user.getContraseña()));
        }
        return userRepository.save(user);
    }

    //Métodos PATCH
    @Override
    public User partialUpdate(User user, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(User.class, key);
            if (field != null) {
                field.setAccessible(true);

                // Si es el campo contraseña, encriptamos
                if ("contraseña".equals(key) && value instanceof String) {
                    value = passwordEncoder.encode((String) value);
                }

                ReflectionUtils.setField(field, user, value);
            }
        });
        return userRepository.save(user);
    }

    //Métodos DELETE
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}


