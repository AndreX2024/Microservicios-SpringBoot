package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "estado_pedido")
@Setter
@Getter
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Long idEstado;

    @Column(name = "estado")
    private String estado;

    @OneToMany(mappedBy = "estado", fetch = FetchType.LAZY)
    @JsonBackReference("order-status-reference")
    private List<Order> pedidos;
}
