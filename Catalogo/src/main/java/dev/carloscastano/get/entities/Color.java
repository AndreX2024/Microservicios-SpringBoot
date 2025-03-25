package dev.carloscastano.get.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Entity
@Table(name = "color")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_color;
    private String nombre;
}
