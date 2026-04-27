package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.IDENTIFICACION_TIPO_IDENTIFICACION;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.TIPO_IDENTIFICACION;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = IDENTIFICACION_TIPO_IDENTIFICACION)
public class IdentificacionTipoIdentificacionEntidad {
    @Id
    private UUID identificador;

    @ManyToOne
    @JoinColumn(name = TIPO_IDENTIFICACION, nullable = false)
    private TipoIdentificacionEntidad tipoIdentificacion;
}