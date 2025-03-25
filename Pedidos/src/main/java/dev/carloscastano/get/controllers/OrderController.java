package dev.carloscastano.get.controllers;


import dev.carloscastano.get.entities.Order;
import dev.carloscastano.get.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService service;

    @GetMapping
    public List<Order> getAll() {return service.getAll();}

    @GetMapping("/user/{idUsuario}")
    public List<Order> getOrdersByUser(@PathVariable Long idUsuario) {
        return service.obtenerPedidoUsuario(idUsuario);
    }

}
