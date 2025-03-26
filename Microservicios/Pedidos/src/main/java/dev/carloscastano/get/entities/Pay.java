package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference  // Correcto: evita la recursión infinita al no serializar 'pedido' en 'Pay'
    private Order pedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_metodo", nullable = false)
    @JsonManagedReference
    private PayMethod metodoPago;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado_pago", nullable = false)
    @JsonManagedReference
    private PayStatus estadoPago;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_pago")
    private Date fecha_pago;

    @Column(name = "monto")
    private Double monto;
}
