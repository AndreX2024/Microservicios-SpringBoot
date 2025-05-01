package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tipo_direccion")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AddressType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_direccion")
    private Long idTipoDireccion;

    @Column(name = "nombre_tipo_direccion")
    private String nombreTipoDireccion;

    @OneToMany(mappedBy = "tipoDireccion", fetch = FetchType.LAZY)
    @JsonBackReference("address-type-reference")
    private List<Address> addressType;
}
