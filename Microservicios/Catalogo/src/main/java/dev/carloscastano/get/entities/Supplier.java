package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "proveedor")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Long idProveedor;

    private String nombre;
    private String telefono;

    @OneToMany(mappedBy = "proveedor", fetch = FetchType.LAZY)
    @JsonBackReference("product-supplier-reference")
    private List<Product> productos;
}