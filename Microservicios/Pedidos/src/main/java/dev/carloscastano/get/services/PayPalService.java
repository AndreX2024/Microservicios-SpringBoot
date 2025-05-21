package dev.carloscastano.get.services;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import dev.carloscastano.get.entities.Pay;
import dev.carloscastano.get.entities.PayMethod;
import dev.carloscastano.get.entities.PayStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PayPalService {

    @Autowired
    private APIContext apiContext;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IPayService payService;

    @Autowired
    private IPayMethodService payMethodService;

    @Autowired IPayStatusService payStatusService;

    public String createPayment(Double amount, String cancelUrl, String successUrl, Long pedidoId, Double amountInCOP) throws PayPalRESTException {
        Amount paymentAmount = new Amount();
        paymentAmount.setCurrency("USD");
        String formattedAmount = new BigDecimal(amount.toString())
                .setScale(2, RoundingMode.HALF_UP)
                .toString();
        paymentAmount.setTotal(formattedAmount);

        Transaction transaction = new Transaction();
        transaction.setDescription("Pago por pedido #" + pedidoId);
        transaction.setAmount(paymentAmount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        Payment createdPayment = payment.create(apiContext);

        // Obtener o crear el pago
        Pay pay = payService.findByPedido_IdPedido(pedidoId)
                .orElseGet(() -> {
                    Pay newPay = new Pay();
                    newPay.setPedido(orderService.findById(pedidoId).orElseThrow());

                    // Usar PayMethod "PayPal"
                    PayMethod paypalMethod = payMethodService.findByMetodo("PayPal").orElseThrow();
                    newPay.setMetodoPago(paypalMethod);

                    // Estado inicial: Pendiente
                    PayStatus pendingStatus = payStatusService.findByEstado("Pendiente").orElseThrow();
                    newPay.setEstadoPago(pendingStatus);

                    // Guardar fecha actual
                    newPay.setFechaPago(new Date());

                    return newPay;
                });

        // Asignar campos necesarios
        pay.setIdPagoExterno(createdPayment.getId());
        pay.setMonto(amountInCOP);

        payService.save(pay);

        return getApprovalUrl(createdPayment);
    }

    private String getApprovalUrl(Payment payment) {
        for (Links links : payment.getLinks()) {
            if (links.getRel().equalsIgnoreCase("approval_url")) {
                return links.getHref();
            }
        }
        return null;
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }
}