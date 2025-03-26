package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "estado_pedido")
@Setter
@Getter
@EqualsAndHashCode
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Integer idEstado;

    @Column(name = "estado")
    private String estado;

    @OneToMany(mappedBy = "estado", fetch = FetchType.EAGER)
    @JsonBackReference  // Correcto: no se serializa la lista de 'pedidos' en 'OrderStatus'
    private List<Order> pedidos;
}

