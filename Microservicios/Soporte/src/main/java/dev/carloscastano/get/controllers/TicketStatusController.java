package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Ticket;
import dev.carloscastano.get.entities.TicketStatus;
import dev.carloscastano.get.services.ITicketStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ticket-statuses")
public class TicketStatusController {

    @Autowired
    private ITicketStatusService ticketStatusService;

    // Métodos GET
    @GetMapping
    public ResponseEntity<List<TicketStatus>> getAllTicketStatuses() {
        return new ResponseEntity<>(ticketStatusService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketStatus> getTicketStatusById(@PathVariable Integer id) {
        Optional<TicketStatus> status = ticketStatusService.findById(id);
        return status.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<TicketStatus> getTicketStatusByNombre(@PathVariable String nombre) {
        Optional<TicketStatus> status = ticketStatusService.findByNombreEstado(nombre);
        return status.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{statusId}/tickets")
    public ResponseEntity<List<Ticket>> getTicketsByStatusId(@PathVariable Integer statusId) {
        List<Ticket> tickets = ticketStatusService.findTicketsByStatusId(statusId);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    // Métodos POST
    @PostMapping
    public ResponseEntity<TicketStatus> createTicketStatus(@Validated @RequestBody TicketStatus ticketStatus) {
        TicketStatus newStatus = ticketStatusService.save(ticketStatus);
        return new ResponseEntity<>(newStatus, HttpStatus.CREATED);
    }

    // Métodos PUT
    @PutMapping("/{id}")
    public ResponseEntity<TicketStatus> updateTicketStatus(@PathVariable Integer id, @Validated @RequestBody TicketStatus updatedStatus) {
        Optional<TicketStatus> existingStatus = ticketStatusService.findById(id);
        if (existingStatus.isPresent()) {
            updatedStatus.setIdEstado(id);
            TicketStatus savedStatus = ticketStatusService.save(updatedStatus);
            return new ResponseEntity<>(savedStatus, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<TicketStatus> partialUpdateTicketStatus(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        Optional<TicketStatus> existingStatus = ticketStatusService.findById(id);
        if (existingStatus.isPresent()) {
            TicketStatus statusToUpdate = existingStatus.get();
            TicketStatus updatedStatus = ticketStatusService.partialUpdate(statusToUpdate, updates);
            return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicketStatus(@PathVariable Integer id) {
        if (ticketStatusService.findById(id).isPresent()) {
            ticketStatusService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}