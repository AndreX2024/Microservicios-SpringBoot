package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "proveedor")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_proveedor;

    private String nombre;
    private String telefono;

    @OneToMany(mappedBy = "proveedor", fetch = FetchType.LAZY)
    @JsonManagedReference("product-supplier-reference")
    private List<Product> productos;
}