package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedido")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "total")
    private Double total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado", nullable = false)
    private OrderStatus estado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("order-details-referecence")
    private List<OrderDetails> detalles;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("order-pay-reference")
    private Pay pago;
}