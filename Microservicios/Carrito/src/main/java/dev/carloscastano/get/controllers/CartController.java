package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Cart;
import dev.carloscastano.get.entities.CartItems;
import dev.carloscastano.get.services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    // Métodos para Cart
    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.findAll();
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        Optional<Cart> cart = cartService.findById(id);
        return cart.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        Optional<Cart> cart = cartService.findByUserId(userId);
        return cart.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        Cart savedCart = cartService.save(cart);
        return new ResponseEntity<>(savedCart, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody Cart cart) {
        Cart updatedCart = cartService.updateCart(id, cart);
        if (updatedCart != null) {
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cart> partialUpdateCart(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Cart updatedCart = cartService.partialUpdateCart(id, updates);
        if (updatedCart != null) {
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Métodos para CartItems
    // Métodos GET
    @GetMapping("/cart-items")
    public ResponseEntity<List<CartItems>> getAllCartItems() {
        List<CartItems> items = cartService.findAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/cart-items/{id}")
    public ResponseEntity<CartItems> getCartItemById(@PathVariable Long id) {
        Optional<CartItems> item = cartService.findItemById(id);
        return item.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/carts/{cartId}/items")
    public ResponseEntity<List<CartItems>> getCartItemsByCartId(@PathVariable Long cartId) {
        List<CartItems> items = cartService.findItemsByCartId(cartId);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    //Métodos POST
    @PostMapping("/cart-items")
    public ResponseEntity<CartItems> createCartItem(@RequestBody CartItems item) {
        CartItems savedItem = cartService.saveItem(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    //Métodos PUT
    @PutMapping("/cart-items/{id}")
    public ResponseEntity<CartItems> updateCartItem(@PathVariable Long id, @RequestBody CartItems item) {
        CartItems updatedItem = cartService.updateCartItem(id, item);
        if (updatedItem != null) {
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Métodos PATCH
    @PatchMapping("/cart-items/{id}")
    public ResponseEntity<CartItems> partialUpdateCartItem(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        CartItems updatedItem = cartService.partialUpdateCartItem(id, updates);
        if (updatedItem != null) {
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Métodos DELETE
    @DeleteMapping("/cart-items/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        cartService.deleteItemById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
