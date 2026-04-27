package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilFecha.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;

@Getter
public class EjecucionActividad {
    private UUID identificador;
    private LocalDate fechaProgramada;
    private LocalDate fechaRealizacion;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private EstadoActividad estado;
    private Actividad actividad;

    private EjecucionActividad(
            UUID identificador,
            LocalDate fechaProgramada,
            LocalDate fechaRealizacion,
            LocalTime horaInicio,
            LocalTime horaFin,
            EstadoActividad estado,
            Actividad actividad
    ) {
        this.fechaProgramada = fechaProgramada;
        this.identificador = identificador;
        this.fechaRealizacion = fechaRealizacion;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
        this.actividad = actividad;
    }

    public static EjecucionActividad construir(
            UUID identificador,
            LocalDate fechaProgramada,
            LocalDate fechaRealizacion,
            LocalTime horaInicio,
            LocalTime horaFin,
            EstadoActividad estado,
            Actividad actividad
    ) {
        return new EjecucionActividad(
                identificador,
                obtenerValorFechaPorDefecto(fechaProgramada),
                obtenerValorFechaPorDefecto(fechaRealizacion),
                obtenerValorHoraPorDefecto(horaInicio),
                obtenerValorHoraPorDefecto(horaFin),
                obtenerObjetoPorDefecto(estado, EstadoActividad.construir()),
                obtenerObjetoPorDefecto(actividad, Actividad.construir())
        );
    }

    public static EjecucionActividad construir() {
        return new EjecucionActividad(
                obtenerValorDefecto(),
                obtenerFechaDefecto(),
                obtenerFechaDefecto(),
                obtenerHoraDefecto(),
                obtenerHoraDefecto(),
                EstadoActividad.construir(),
                Actividad.construir()
        );
    }

    public void actualizarFechaProgramada(LocalDate fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public void actualizarHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void actualizarHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public void actualizarFechaRealizacion(LocalDate fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public void actualizarEstado(EstadoActividad estado) {
        this.estado = estado;
    }
}