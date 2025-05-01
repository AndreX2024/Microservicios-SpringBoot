package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Cart;
import dev.carloscastano.get.entities.CartItems;
import dev.carloscastano.get.repository.CartItemsRepository;
import dev.carloscastano.get.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    // Métodos para Cart
    // Métodos GET
    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public List<Cart> findAll() {
        return (List<Cart>) cartRepository.findAll();
    }

    @Override
    public Optional<Cart> findByUserId(Long userId) {
        return cartRepository.findByIdUsuario(userId);
    }

    //Métodos POST
    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    //Métodos PUT
    @Override
    public Cart updateCart(Long id, Cart cart) {
        Cart existingCart = cartRepository.findById(id).orElse(null);
        if (existingCart != null) {
            existingCart.setIdUsuario(cart.getIdUsuario());
            return cartRepository.save(existingCart);
        }
        return null;
    }

    //Métodos PATCH
    @Override
    public Cart partialUpdateCart(Long id, Map<String, Object> updates) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart != null) {
            updates.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Cart.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    // Convert value to the correct type (Long in this case)
                    if ("idUsuario".equals(key) && value instanceof Integer) {
                        value = ((Integer) value).longValue();
                    }
                    ReflectionUtils.setField(field, cart, value);
                }
            });
            return cartRepository.save(cart);
        }
        return null;
    }

    // Métodos DELETE
    @Override
    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }

    // Métodos para CartItems
    //Métodos GET
    @Override
    public Optional<CartItems> findItemById(Long id) {
        return cartItemsRepository.findById(id);
    }

    @Override
    public List<CartItems> findAllItems() {
        return (List<CartItems>) cartItemsRepository.findAll();
    }

    @Override
    public List<CartItems> findItemsByCartId(Long cartId) {
        return cartItemsRepository.findByCarrito_IdCarrito(cartId);
    }

    //Métodos POST
    @Override
    public CartItems saveItem(CartItems item) {
        return cartItemsRepository.save(item);
    }

    //Métodos PUT
    @Override
    public CartItems updateCartItem(Long id, CartItems item) {
        CartItems existingItem = cartItemsRepository.findById(id).orElse(null);
        if (existingItem != null) {
            existingItem.setCarrito(item.getCarrito());
            existingItem.setIdProducto(item.getIdProducto());
            existingItem.setIdTalla(item.getIdTalla());
            existingItem.setIdColor(item.getIdColor());
            existingItem.setCantidad(item.getCantidad());
            existingItem.setPrecioUnitario(item.getPrecioUnitario());
            return cartItemsRepository.save(existingItem);
        }
        return null;
    }

    //Métodos PATCH
    @Override
    public CartItems partialUpdateCartItem(Long id, Map<String, Object> updates) {
        CartItems item = cartItemsRepository.findById(id).orElse(null);
        if (item != null) {
            updates.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(CartItems.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, item, value);
                }
            });
            return cartItemsRepository.save(item);
        }
        return null;
    }

    // Métodos DELETE
    @Override
    public void deleteItemById(Long id) {
        cartItemsRepository.deleteById(id);
    }

}