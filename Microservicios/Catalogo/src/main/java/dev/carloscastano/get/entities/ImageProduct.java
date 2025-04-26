package dev.carloscastano.get.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "imagen_producto")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ImageProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_imagen;

    @Column(name = "url_imagen", nullable = false)
    private String urlImagen;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    @JsonBackReference("product-image-reference")
    private Product producto;
}
