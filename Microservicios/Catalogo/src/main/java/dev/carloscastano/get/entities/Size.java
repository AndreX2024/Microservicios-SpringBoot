package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "talla")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_talla")
    private Long idTalla;

    private String nombre;

    @OneToMany(mappedBy = "talla", fetch = FetchType.LAZY)
    @JsonBackReference("inventory-size-reference")
    private List<Inventory> inventarios;
}
