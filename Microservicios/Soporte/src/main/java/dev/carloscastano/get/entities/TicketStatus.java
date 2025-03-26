package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "estado_ticket")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class TicketStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Integer idEstado;

    @Column(name = "nombre_estado")
    private String nombreEstado;

    @OneToMany(mappedBy = "estado", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Ticket> tickets;



}
