package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "metodo_pago")
@Setter
@Getter
@EqualsAndHashCode
public class PayMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metodo")
    private Integer idMetodo;

    @Column(name = "metodo")
    private String metodo;

    @OneToMany(mappedBy = "metodoPago", fetch = FetchType.EAGER)
    @JsonBackReference  // Correcto: no se serializa la lista de 'pago' en 'PayMethod'
    private List<Pay> pago;
}

