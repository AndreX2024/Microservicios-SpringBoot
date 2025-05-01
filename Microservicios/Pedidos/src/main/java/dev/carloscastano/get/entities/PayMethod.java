    package dev.carloscastano.get.entities;

    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import lombok.EqualsAndHashCode;
    import lombok.Getter;
    import lombok.Setter;

    import jakarta.persistence.*;
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
        private Long idMetodo;

        @Column(name = "metodo")
        private String metodo;

        @OneToMany(mappedBy = "metodoPago", fetch = FetchType.LAZY)
        @JsonManagedReference("pay-method-reference")
        private List<Pay> pago;
    }
