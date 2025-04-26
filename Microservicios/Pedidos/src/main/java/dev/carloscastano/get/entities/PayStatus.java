package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "estado_pago")
@Setter
@Getter
@EqualsAndHashCode
public class PayStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_pago")
    private Long idEstadoPago;

    @Column(name = "estado")
    private String estado;

    @OneToMany(mappedBy = "estadoPago", fetch = FetchType.EAGER)
    @JsonManagedReference("pay-status-reference")
    private List<Pay> pago;
}
