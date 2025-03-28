package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "detalle_pedido")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long id_detalle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pedido")
    @JsonBackReference  // Correcto: evita la serialización de 'pedido' en 'OrderDetails'
    private Order pedido;

    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "id_talla")
    private Long idTalla;

    @Column(name = "id_color")
    private Long idColor;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_unitario")
    private Double precioUnitario;
}
