package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "inventario")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Long idInventario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    @JsonBackReference("product-inventory-reference")
    private Product producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_talla", nullable = false)
    private Size talla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_color", nullable = false)
    private Color color;

    private Integer stock;
}
