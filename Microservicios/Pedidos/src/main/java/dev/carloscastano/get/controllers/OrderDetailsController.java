package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.OrderDetails;
import dev.carloscastano.get.repository.OrderDetailsRepository;
import dev.carloscastano.get.services.IOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order-details")
public class OrderDetailsController {

    @Autowired
    private IOrderDetailsService service;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    // Obtener detalles de un pedido específico por su ID de pedido
    @GetMapping("/order/{idPedido}")
    public List<OrderDetails> getOrderDetailsByOrderId(@PathVariable Long idPedido) {
        return service.getByOrderId(idPedido);
    }

    // Obtener un detalle de pedido específico por su ID
    @GetMapping("/{idDetalle}")
    public ResponseEntity<OrderDetails> getOrderDetailsById(@PathVariable Long idDetalle) {
        Optional<OrderDetails> orderDetails = service.getById(idDetalle);
        return orderDetails.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{idPedido}/items")
    public OrderDetails agregarItemAlPedido(@PathVariable Long idPedido, @RequestBody OrderDetails item) {
        return service.agregarItemAlPedido(idPedido, item);
    }



    @PutMapping("/{id}")
    public ResponseEntity<OrderDetails> updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetails newDetail) {
        Optional<OrderDetails> existingDetail = service.getById(id);
        if (existingDetail.isPresent()) {
            OrderDetails detail = existingDetail.get();
            detail.setCantidad(newDetail.getCantidad());
            detail.setPrecioUnitario(newDetail.getPrecioUnitario());
            detail.setIdProducto(newDetail.getIdProducto());
            detail.setIdColor(newDetail.getIdColor());
            detail.setIdTalla(newDetail.getIdTalla());
            return new ResponseEntity<>(orderDetailsRepository.save(detail), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDetails> patchOrderDetail(@PathVariable Long id, @RequestBody OrderDetails details) {
        Optional<OrderDetails> optionalDetail = service.getById(id);
        if (optionalDetail.isPresent()) {
            OrderDetails detail = optionalDetail.get();
            if (details.getCantidad() != null) detail.setCantidad(details.getCantidad());
            if (details.getPrecioUnitario() != null) detail.setPrecioUnitario(details.getPrecioUnitario());
            if (details.getIdProducto() != null) detail.setIdProducto(details.getIdProducto());
            if (details.getIdColor() != null) detail.setIdColor(details.getIdColor());
            if (details.getIdTalla() != null) detail.setIdTalla(details.getIdTalla());
            return new ResponseEntity<>(orderDetailsRepository.save(detail), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
