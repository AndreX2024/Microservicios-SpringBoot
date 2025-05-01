package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "color")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_color")
    private Long idColor;

    private String nombre;

    @OneToMany(mappedBy = "color", fetch = FetchType.LAZY)
    @JsonBackReference("inventory-color-reference")
    private List<Inventory> inventarios;

}
