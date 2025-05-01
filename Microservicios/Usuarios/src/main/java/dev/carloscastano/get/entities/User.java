package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    private Long documento;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String contraseña;

    @ManyToOne
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    private Role rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("user-address-reference")
    private List<Address> direcciones;
}

