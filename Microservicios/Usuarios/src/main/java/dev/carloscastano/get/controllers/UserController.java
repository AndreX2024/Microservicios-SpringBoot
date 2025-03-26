package dev.carloscastano.get.controllers;


import dev.carloscastano.get.entities.User;
import dev.carloscastano.get.repository.UserRepository;
import dev.carloscastano.get.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService service;

    @GetMapping
    public List<User> getAll() {return service.getAll();}

}
