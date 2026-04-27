package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = IDENTIFICACION)
public class IdentificacionEntidad {
    @Id
    private UUID identificador;

    @Column(name = NUMERO_IDENTIFICACION, length = 12, nullable = false)
    private String numeroIdentificacion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = TIPO_IDENTIFICACION)
    private IdentificacionTipoIdentificacionEntidad tipoIdentificacion;
}