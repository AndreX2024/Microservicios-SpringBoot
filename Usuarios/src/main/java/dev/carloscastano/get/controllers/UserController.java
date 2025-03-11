package dev.carloscastano.get.controllers;


import dev.carloscastano.get.entities.User;
import dev.carloscastano.get.repository.UserRepository;
import dev.carloscastano.get.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private IUserService service;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("users")
    public List<User> getAll() {return service.getAll();}

}
