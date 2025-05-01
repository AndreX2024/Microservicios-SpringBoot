package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Inventory;
import dev.carloscastano.get.services.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private IInventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories() {
        return new ResponseEntity<>(inventoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        Optional<Inventory> inventory = inventoryService.findById(id);
        return inventory.map(inv -> new ResponseEntity<>(inv, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Inventory>> getInventoriesByProductId(@PathVariable Long productId) {
        List<Inventory> inventories = inventoryService.findByProductId(productId);
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }

    @GetMapping("/size/{sizeId}")
    public ResponseEntity<List<Inventory>> getInventoriesBySizeId(@PathVariable Long sizeId) {
        List<Inventory> inventories = inventoryService.findBySizeId(sizeId);
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }

    @GetMapping("/color/{colorId}")
    public ResponseEntity<List<Inventory>> getInventoriesByColorId(@PathVariable Long colorId) {
        List<Inventory> inventories = inventoryService.findByColorId(colorId);
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@Validated @RequestBody Inventory inventory) {
        Inventory newInventory = inventoryService.save(inventory);
        return new ResponseEntity<>(newInventory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @Validated @RequestBody Inventory updatedInventory) {
        Optional<Inventory> existingInventory = inventoryService.findById(id);
        if (existingInventory.isPresent()) {
            updatedInventory.setIdInventario(id);
            Inventory savedInventory = inventoryService.save(updatedInventory);
            return new ResponseEntity<>(savedInventory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Inventory> partialUpdateInventory(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Inventory> existingInventory = inventoryService.findById(id);
        if (existingInventory.isPresent()) {
            Inventory inventoryToUpdate = existingInventory.get();
            Inventory updatedInventory = inventoryService.partialUpdate(inventoryToUpdate, updates);
            return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        if (inventoryService.findById(id).isPresent()) {
            inventoryService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}