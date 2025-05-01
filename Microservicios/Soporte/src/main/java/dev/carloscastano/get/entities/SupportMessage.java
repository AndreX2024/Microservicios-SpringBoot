package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
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
    @Column(name = "id_mensaje")
    private Long idMensaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ticket")
    @JsonBackReference
    private Ticket ticket;

    @Column(columnDefinition = "TEXT")
    private String mensaje;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_envio")
    private Date fechaEnvio;
}
