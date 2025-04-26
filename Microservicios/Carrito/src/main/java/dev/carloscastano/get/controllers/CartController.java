package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Cart;
import dev.carloscastano.get.entities.CartItems;
import dev.carloscastano.get.repository.CartItemRepository;
import dev.carloscastano.get.repository.CartRepository;
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
    private ICartService service;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    // Métodos GET
    @GetMapping
    public List<Cart> getAll() {
        return service.getAll();
    }

    @GetMapping("/user/{idUsuario}")
    public Cart obtenerCarrito(@PathVariable Long idUsuario) {
        return service.obtenerCarrito(idUsuario);
    }

    @GetMapping("/{idCarrito}/items")
    public List<CartItems> obtenerItemsDelCarrito(@PathVariable Long idCarrito) {
        return service.obtenerItemsDelCarrito(idCarrito);
    }


    // Métodos POST
    @PostMapping
    public Cart crearCarrito(@RequestBody Cart carrito) {
        return service.crearCarrito(carrito);
    }

    @PostMapping("/{idCarrito}/items")
    public CartItems agregarItemAlCarrito(@PathVariable Long idCarrito, @RequestBody CartItems item) {
        return service.agregarItemAlCarrito(idCarrito, item);
    }



    // Métodos PUT
    @PutMapping("/{idCarrito}/items/update")
    public ResponseEntity<CartItems> updateItems(@PathVariable Long idCarrito, @RequestBody CartItems items){
        Optional<CartItems> cartdata = cartItemRepository.findById(idCarrito);
        if (cartdata.isPresent()){
            CartItems cartItemsData = cartdata.get();
            cartItemsData.setIdProducto(items.getIdProducto());
            cartItemsData.setIdTalla(items.getIdTalla());
            cartItemsData.setIdColor(items.getIdColor());
            cartItemsData.setCantidad(items.getCantidad());
            cartItemsData.setPrecioUnitario(items.getPrecioUnitario());
            return new ResponseEntity<>(cartItemRepository.save(cartItemsData), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
    }


    @PatchMapping("/{idCarrito}/items/{idItem}")
    public ResponseEntity<?> actualizarItemEnCarrito(
            @PathVariable("idCarrito") Long idCarrito,
            @PathVariable("idItem") Long idItem,
            @RequestBody Map<String, Object> updates) {

        // 1. Verificar que el carrito existe
        Optional<Cart> cartData = cartRepository.findById(idCarrito);
        if (cartData.isEmpty()) {
            return new ResponseEntity<>("Carrito no encontrado con ID: " + idCarrito, HttpStatus.NOT_FOUND);
        }

        // 2. Verificar que el ítem existe y pertenece a este carrito
        Optional<CartItems> itemData = cartItemRepository.findById(idItem);
        if (itemData.isEmpty() || !itemData.get().getCarrito().getIdCarrito().equals(idCarrito)) {
            return new ResponseEntity<>("Ítem no encontrado en este carrito", HttpStatus.NOT_FOUND);
        }

        CartItems existingItem = itemData.get();

        // 3. Actualizar solo los campos proporcionados
        if (updates.containsKey("cantidad")) {
            existingItem.setCantidad(Integer.valueOf(updates.get("cantidad").toString()));
        }
        if (updates.containsKey("precioUnitario")) {
            existingItem.setPrecioUnitario(Double.valueOf(updates.get("precioUnitario").toString()));
        }
        if (updates.containsKey("idProducto")) {
            existingItem.setIdProducto(Long.valueOf(updates.get("idProducto").toString()));
        }
        if (updates.containsKey("idTalla")) {
            String tallaValue = updates.get("idTalla").toString();
            existingItem.setIdTalla(tallaValue.isEmpty() ? null : Long.valueOf(tallaValue));
        }
        if (updates.containsKey("idColor")) {
            String colorValue = updates.get("idColor").toString();
            existingItem.setIdColor(colorValue.isEmpty() ? null : Long.valueOf(colorValue));
        }

        // 4. Guardar los cambios
        return new ResponseEntity<>(cartItemRepository.save(existingItem), HttpStatus.ACCEPTED);
    }
}