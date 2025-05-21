package dev.carloscastano.get.controllers;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import dev.carloscastano.get.entities.OrderDetails;
import dev.carloscastano.get.entities.Pay;
import dev.carloscastano.get.entities.PayStatus;
import dev.carloscastano.get.repository.PayStatusRepository;
import dev.carloscastano.get.services.IOrderDetailsService;
import dev.carloscastano.get.services.IOrderService;
import dev.carloscastano.get.services.IPayService;
import dev.carloscastano.get.services.PayPalService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paypal")
public class PayPalController {

    @Autowired
    private PayPalService payPalService;

    @Autowired
    private IPayService payService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailsService orderDetailsService;

    @Autowired
    private PayStatusRepository payStatusRepository;

    @PostMapping("/create-payment")
    public ResponseEntity<String> createPayment(@RequestParam Double monto,
                                                @RequestParam Double montoCop,
                                                @RequestParam Long pedidoId) {
        try {
            String approvalUrl = payPalService.createPayment(
                    monto,
                    "http://localhost:8080/paypal/cancel?pedidoId=" + pedidoId, // 👈 Incluir pedidoId
                    "http://localhost:8080/paypal/success?pedidoId=" + pedidoId,
                    pedidoId,
                    montoCop
            );
            return ResponseEntity.ok(approvalUrl);
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el pago.");
        }
    }

    @GetMapping("/success")
    public void paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,
            @RequestParam("pedidoId") Long pedidoId,
            HttpServletResponse response) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            if ("approved".equals(payment.getState())) {
                Optional<Pay> optionalPay = payService.findByPedido_IdPedido(pedidoId);
                optionalPay.ifPresent(pay -> {
                    pay.setIdPagoExterno(payment.getId());
                    PayStatus status = payStatusRepository.findById(2L).orElseThrow();
                    pay.setEstadoPago(status);
                    payService.save(pay);
                });
                response.sendRedirect("http://localhost:4200/success?status=success&pedidoId=" + pedidoId);
            } else {
                // Eliminar si no está aprobado
                eliminarPedidoYpago(pedidoId);
                response.sendRedirect("http://localhost:4200/success?status=failed");
            }
        } catch (Exception e) {
            eliminarPedidoYpago(pedidoId);
            try {
                response.sendRedirect("http://localhost:4200/success?status=error");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @GetMapping("/cancel")
    public void paymentCancel(@RequestParam("pedidoId") Long pedidoId,
                              HttpServletResponse response) {
        eliminarPedidoYpago(pedidoId);
        try {
            response.sendRedirect("http://localhost:4200/success?status=failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void eliminarPedidoYpago(Long pedidoId) {
        Optional<Pay> payOptional = payService.findByPedido_IdPedido(pedidoId);
        if (payOptional.isPresent()) {
            List<OrderDetails> orderDetailsData = orderDetailsService.findByPedido_IdPedido(pedidoId);
            for (OrderDetails detail : orderDetailsData) {
                orderDetailsService.deleteById(detail.getIdDetalle());
            }
            payService.deleteById(payOptional.get().getIdPago());
            orderService.deleteById(pedidoId);
        }
    }
}