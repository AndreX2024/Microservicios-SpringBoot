package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Cart;
import dev.carloscastano.get.entities.CartItems;
import dev.carloscastano.get.services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService service;

    @GetMapping
    public List<Cart> getAll() {
        return service.getAll();
    }

    @GetMapping("/user/{idUsuario}")
    public Cart obtenerCarrito(@PathVariable Long idUsuario) {
        return service.obtenerCarrito(idUsuario);
    }
}
