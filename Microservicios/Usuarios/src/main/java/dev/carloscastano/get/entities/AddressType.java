package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tipo_direccion")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class AddressType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tipo_direccion;

    @Column(name = "nombre_tipo_direccion")
    private String nombreTipoDireccion;

    @OneToMany(mappedBy = "tipoDireccion", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Address> addressType;
}
