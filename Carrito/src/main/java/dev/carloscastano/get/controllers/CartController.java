package dev.carloscastano.get.controllers;


import dev.carloscastano.get.entities.Cart;
import dev.carloscastano.get.repository.CartRepository;
import dev.carloscastano.get.services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    private ICartService service;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("cart")
    public List<Cart> getAll() {return service.getAll();}

    

}
