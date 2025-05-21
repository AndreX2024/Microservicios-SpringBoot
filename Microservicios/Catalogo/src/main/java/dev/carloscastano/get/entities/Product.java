package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "producto")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    private String nombre;
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Category categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Supplier proveedor;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("product-image-reference")
    private List<ImageProduct> imagenes;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("product-inventory-reference")
    private List<Inventory> inventarios;

    private Double precio;

    @Column(name = "descuento_activo")
    private Boolean descuentoActivo;

    @Column(name = "porcentaje_descuento")
    private Double porcentajeDescuento;
}
