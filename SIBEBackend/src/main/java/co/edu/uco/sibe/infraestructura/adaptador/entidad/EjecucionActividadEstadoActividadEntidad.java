package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.CAMPO_ESTADO_ACTIVIDAD;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.TABLA_EJECUCION_ACTIVIDAD_ESTADO_ACTIVIDAD;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TABLA_EJECUCION_ACTIVIDAD_ESTADO_ACTIVIDAD)
public class EjecucionActividadEstadoActividadEntidad {
    @Id
    private UUID identificador;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = CAMPO_ESTADO_ACTIVIDAD, nullable = false)
    private EstadoActividadEntidad estadoActividad;
}