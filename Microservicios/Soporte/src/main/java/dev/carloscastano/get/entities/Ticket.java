package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ticket")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Long idTicket;

    @Column(name = "id_usuario")
    private Long idUsuario;

    private String asunto;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_creacion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_cierre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado")
    @JsonManagedReference
    private TicketStatus estado;


    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SupportMessage> mensajes;
}
