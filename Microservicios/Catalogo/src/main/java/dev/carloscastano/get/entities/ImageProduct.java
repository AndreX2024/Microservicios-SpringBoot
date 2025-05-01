package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "imagen_producto")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ImageProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen")
    private Long idImagen;

    @Column(name = "url_imagen", nullable = false)
    private String urlImagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    @JsonBackReference("product-image-reference")
    private Product producto;
}
