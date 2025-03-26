package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "proveedor")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_proveedor;
    private String nombre;
    private String telefono;

    @OneToMany(mappedBy = "proveedor", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Product> productos;
}
