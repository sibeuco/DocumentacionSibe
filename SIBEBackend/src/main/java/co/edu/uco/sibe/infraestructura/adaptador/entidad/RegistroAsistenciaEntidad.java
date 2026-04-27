package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import java.util.UUID;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = REGISTRO_ASISTENCIA)
public class RegistroAsistenciaEntidad {
    @Id
    private UUID identificador;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = CAMPO_EJECUCION_ACTIVIDAD, nullable = false)
    private EjecucionActividadEntidad ejecucionActividad;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = PARTICIPANTE, nullable = false)
    private ParticipanteEntidad participante;
}