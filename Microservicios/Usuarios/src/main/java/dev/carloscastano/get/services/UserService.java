package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Address;
import dev.carloscastano.get.entities.Role;
import dev.carloscastano.get.entities.User;
import dev.carloscastano.get.repository.AddressRepository;
import dev.carloscastano.get.repository.RoleRepository;
import dev.carloscastano.get.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<User> getAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    public Optional<User> getUserByDocumento(Long documento) {
        return repository.findByDocumento(documento);
    }

    @Override
    public Optional<List<Address>> getUserAddresses(Long id) {
        return repository.findById(id).map(User::getDirecciones);
    }

    public List<Role> getRoles() {
        return (List<Role>) roleRepository.findAll();  // o alguna lógica para obtener los roles
    }


    @Override
    public User createUser(@RequestBody User user) { return repository.save(user); }

    @Override
    public Address addAddressUser(Long documentoUsuario, Address direccion) {
        User usuario = repository.findByDocumento(documentoUsuario).orElse(null);
        if (usuario != null){
            direccion.setUsuario(usuario);
            return addressRepository.save(direccion);
        }
        return null;
    }

}


