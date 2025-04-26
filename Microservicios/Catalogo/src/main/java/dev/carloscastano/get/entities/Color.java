package dev.carloscastano.get.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "color")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_color;

    private String nombre;
}
