package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.dominio.modelo.Participante;
import co.edu.uco.sibe.dominio.modelo.RegistroAsistencia;
import co.edu.uco.sibe.dominio.puerto.comando.ActividadRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.comando.RegistroAsistenciaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.EstadoActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.RegistroAsistenciaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.service.RegistrarParticipanteService;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.EN_CURSO;
import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.FINALIZADA;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class FinalizarActividadUseCase {
    private final ActividadRepositorioComando actividadRepositorioComando;
    private final ActividadRepositorioConsulta actividadRepositorioConsulta;
    private final EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta;
    private final RegistrarParticipanteService registrarParticipanteService;
    private final RegistroAsistenciaRepositorioComando registroAsistenciaRepositorioComando;
    private final RegistroAsistenciaRepositorioConsulta registroAsistenciaRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    public FinalizarActividadUseCase(ActividadRepositorioComando actividadRepositorioComando,
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta,
            RegistrarParticipanteService registrarParticipanteService,
            RegistroAsistenciaRepositorioComando registroAsistenciaRepositorioComando,
            RegistroAsistenciaRepositorioConsulta registroAsistenciaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        this.actividadRepositorioComando = actividadRepositorioComando;
        this.actividadRepositorioConsulta = actividadRepositorioConsulta;
        this.estadoActividadRepositorioConsulta = estadoActividadRepositorioConsulta;
        this.registrarParticipanteService = registrarParticipanteService;
        this.registroAsistenciaRepositorioComando = registroAsistenciaRepositorioComando;
        this.registroAsistenciaRepositorioConsulta = registroAsistenciaRepositorioConsulta;
        this.autorizacionServicio = autorizacionServicio;
    }

    public UUID ejecutar(UUID identificadorEjecucion, List<Participante> participantes) {
        autorizacionServicio.validarAccesoAEjecucionActividad(identificadorEjecucion);
        var ejecucion = validarSiExisteEjecucion(identificadorEjecucion);

        validarSiEjecucionActividadEstaEnCurso(ejecucion.getEstado());

        var estado = validarSiExisteEstado(FINALIZADA);

        ejecucion.actualizarFechaRealizacion(LocalDate.now());
        ejecucion.actualizarHoraFin(LocalTime.now());
        ejecucion.actualizarEstado(estado);

        actividadRepositorioComando.modificarEjecucion(ejecucion);

        participantes.forEach(participante -> {
            var participanteRegistrado = registrarParticipanteService.ejecutar(participante);

            var registro = RegistroAsistencia.construir(
                    generar(uuid -> !esNulo(registroAsistenciaRepositorioConsulta.consultarPorIdentificador(uuid))),
                    ejecucion,
                    participanteRegistrado);

            registroAsistenciaRepositorioComando.guardar(registro);
        });

        return identificadorEjecucion;
    }

    private EjecucionActividad validarSiExisteEjecucion(UUID id) {
        var ejecucion = actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(id);

        if (esNulo(ejecucion)) {
            throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(EJECUCION_ACTIVIDAD_NO_ENCONTRADA_CON_ID, id));
        }

        return ejecucion;
    }

    private EstadoActividad validarSiExisteEstado(String nombre) {
        var estado = estadoActividadRepositorioConsulta.consultarPorNombre(nombre);

        if (esNulo(estado)) {
            throw new ValorInvalidoExcepcion(
                    obtenerMensajeConParametro(ESTADO_ACTIVIDAD_NO_ENCONTRADO_CON_NOMBRE, nombre));
        }

        return estado;
    }

    private void validarSiEjecucionActividadEstaEnCurso(EstadoActividad estado) {
        if (!estado.getNombre().equals(EN_CURSO)) {
            throw new InvalidOperationException(FINALIZAR_ACTIVIDAD_EN_ESTADO_DIFERENTE_A_EN_CURSO);
        }
    }
}