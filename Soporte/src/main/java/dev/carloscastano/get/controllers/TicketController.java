package dev.carloscastano.get.controllers;


import dev.carloscastano.get.entities.Ticket;
import dev.carloscastano.get.services.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private ITicketService service;

    @GetMapping
    public List<Ticket> getAll() {return service.getAll();}

    @GetMapping("/user/{idUsuario}")
    public List<Ticket> obtenerTicketsPorUsuario(@PathVariable Long idUsuario) {
        return service.obtenerTicketsPorUsuario(idUsuario);
    }

}
