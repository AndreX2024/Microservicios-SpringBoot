package dev.carloscastano.get.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "talla")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_talla;

    private String nombre;
}
