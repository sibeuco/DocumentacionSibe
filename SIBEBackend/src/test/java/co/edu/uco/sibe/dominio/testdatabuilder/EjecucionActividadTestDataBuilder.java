package co.edu.uco.sibe.dominio.testdatabuilder;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import java.time.LocalDate;
import java.util.UUID;

public class EjecucionActividadTestDataBuilder {
    private UUID identificador = UUID.randomUUID();
    private LocalDate fechaProgramada = LocalDate.now().plusDays(7);
    private LocalDate fechaRealizacion = null;
    private java.time.LocalTime horaInicio = null;
    private java.time.LocalTime horaFin = null;
    private EstadoActividad estado = new EstadoActividadTestDataBuilder().construir();
    private Actividad actividad = null;

    public EjecucionActividadTestDataBuilder conIdentificador(UUID identificador) {
        this.identificador = identificador;
        return this;
    }

    public EjecucionActividadTestDataBuilder conFechaProgramada(LocalDate fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
        return this;
    }

    public EjecucionActividadTestDataBuilder conFechaRealizacion(LocalDate fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
        return this;
    }

    public EjecucionActividadTestDataBuilder conEstado(EstadoActividad estado) {
        this.estado = estado;
        return this;
    }

    public EjecucionActividadTestDataBuilder conActividad(Actividad actividad) {
        this.actividad = actividad;
        return this;
    }

    public EjecucionActividad construir() {
        return EjecucionActividad.construir(identificador, fechaProgramada, fechaRealizacion,
                horaInicio, horaFin, estado, actividad);
    }
}
