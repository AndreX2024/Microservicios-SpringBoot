package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "direccion")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_direccion;
    private String calle;
    private String ciudad;
    private String departamento;
    private String codigo_postal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_direccion")
    @JsonManagedReference
    private AddressType tipoDireccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    @JsonBackReference
    private User usuario;
}


