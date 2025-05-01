package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.SupportMessage;
import dev.carloscastano.get.entities.Ticket;
import dev.carloscastano.get.services.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private ITicketService ticketService;

    // Métodos GET
    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return new ResponseEntity<>(ticketService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketService.findById(id);
        return ticket.map(t -> new ResponseEntity<>(t, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Ticket>> getTicketsByUserId(@PathVariable Long userId) {
        List<Ticket> tickets = ticketService.findByUserId(userId);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<List<Ticket>> getTicketsByStatusId(@PathVariable Integer statusId) {
        List<Ticket> tickets = ticketService.findByStatusId(statusId);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/{ticketId}/messages")
    public ResponseEntity<List<SupportMessage>> getTicketMessages(@PathVariable Long ticketId) {
        List<SupportMessage> messages = ticketService.findMessagesByTicketId(ticketId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    // Métodos POST
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@Validated @RequestBody Ticket ticket) {
        Ticket newTicket = ticketService.save(ticket);
        return new ResponseEntity<>(newTicket, HttpStatus.CREATED);
    }

    // Métodos PUT
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @Validated @RequestBody Ticket updatedTicket) {
        Optional<Ticket> existingTicket = ticketService.findById(id);
        if (existingTicket.isPresent()) {
            updatedTicket.setIdTicket(id);
            Ticket savedTicket = ticketService.save(updatedTicket);
            return new ResponseEntity<>(savedTicket, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<Ticket> partialUpdateTicket(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Ticket> existingTicket = ticketService.findById(id);
        if (existingTicket.isPresent()) {
            Ticket ticketToUpdate = existingTicket.get();
            Ticket updatedTicket = ticketService.partialUpdate(ticketToUpdate, updates);
            return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        if (ticketService.findById(id).isPresent()) {
            ticketService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}