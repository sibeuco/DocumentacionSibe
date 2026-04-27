package co.edu.uco.sibe.infraestructura.adaptador.entidad;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.PersistenciaConstante.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TABLA_EJECUCION_ACTIVIDAD)
public class EjecucionActividadEntidad {
    @Id
    @Column(name = CAMPO_IDENTIFICADOR, nullable = false, updatable = false)
    private UUID identificador;

    @Column(name = CAMPO_FECHA_PROGRAMADA, nullable = false)
    private LocalDate fechaProgramada;

    @Column(name = CAMPO_FECHA_REALIZACION)
    private LocalDate fechaRealizacion;

    @Column(name = CAMPO_HORA_INICIO)
    private LocalTime horaInicio;

    @Column(name = CAMPO_HORA_FIN)
    private LocalTime horaFin;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = CAMPO_EJECUCION_ACTIVIDAD, nullable = false)
    private EjecucionActividadEstadoActividadEntidad estadoActividad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CAMPO_ACTIVIDAD_ID, nullable = false)
    private ActividadEntidad actividad;
}