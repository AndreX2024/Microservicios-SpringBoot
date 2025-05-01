package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.SupportMessage;
import dev.carloscastano.get.services.ISupportMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/support-messages")
public class SupportMessageController {

    @Autowired
    private ISupportMessageService supportMessageService;

    // Métodos GET
    @GetMapping
    public ResponseEntity<List<SupportMessage>> getAllSupportMessages() {
        return new ResponseEntity<>(supportMessageService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupportMessage> getSupportMessageById(@PathVariable Long id) {
        Optional<SupportMessage> message = supportMessageService.findById(id);
        return message.map(m -> new ResponseEntity<>(m, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<SupportMessage>> getMessagesByTicketId(@PathVariable Long ticketId) {
        List<SupportMessage> messages = supportMessageService.findByTicketId(ticketId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    // Métodos POST
    @PostMapping
    public ResponseEntity<SupportMessage> createSupportMessage(@Validated @RequestBody SupportMessage supportMessage) {
        SupportMessage newMessage = supportMessageService.save(supportMessage);
        return new ResponseEntity<>(newMessage, HttpStatus.CREATED);
    }

    // Métodos PUT
    @PutMapping("/{id}")
    public ResponseEntity<SupportMessage> updateSupportMessage(@PathVariable Long id, @Validated @RequestBody SupportMessage updatedMessage) {
        Optional<SupportMessage> existingMessage = supportMessageService.findById(id);
        if (existingMessage.isPresent()) {
            updatedMessage.setIdMensaje(id);
            SupportMessage savedMessage = supportMessageService.save(updatedMessage);
            return new ResponseEntity<>(savedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<SupportMessage> partialUpdateSupportMessage(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<SupportMessage> existingMessage = supportMessageService.findById(id);
        if (existingMessage.isPresent()) {
            SupportMessage messageToUpdate = existingMessage.get();
            SupportMessage updatedMessage = supportMessageService.partialUpdate(messageToUpdate, updates);
            return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupportMessage(@PathVariable Long id) {
        if (supportMessageService.findById(id).isPresent()) {
            supportMessageService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}