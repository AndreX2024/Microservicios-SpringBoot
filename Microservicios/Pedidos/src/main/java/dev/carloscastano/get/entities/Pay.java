package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pago")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long id_pago;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pedido", nullable = false)
    @JsonBackReference("order-pay-reference")
    private Order pedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_metodo", nullable = false)
    @JsonBackReference("pay-method-reference")
    private PayMethod metodoPago;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado_pago", nullable = false)
    @JsonBackReference("pay-status-reference")
    private PayStatus estadoPago;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_pago")
    private Date fecha_pago;

    @Column(name = "monto")
    private Double monto;
}