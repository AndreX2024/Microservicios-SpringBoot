package dev.carloscastano.get.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "talla")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_talla;
    private String nombre;
}
