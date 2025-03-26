package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mensaje_soporte")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class SupportMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_mensaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ticket")
    @JsonBackReference
    private Ticket ticket;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(columnDefinition = "TEXT")
    private String mensaje;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_envio;
}
