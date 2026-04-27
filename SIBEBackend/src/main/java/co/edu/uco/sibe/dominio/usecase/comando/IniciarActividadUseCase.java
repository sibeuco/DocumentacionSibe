package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.dominio.puerto.comando.ActividadRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.EstadoActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;

import java.time.LocalTime;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.EN_CURSO;
import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.PENDIENTE;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class IniciarActividadUseCase {
    private final ActividadRepositorioComando actividadRepositorioComando;
    private final ActividadRepositorioConsulta actividadRepositorioConsulta;
    private final EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    public IniciarActividadUseCase(ActividadRepositorioComando actividadRepositorioComando,
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        this.actividadRepositorioComando = actividadRepositorioComando;
        this.actividadRepositorioConsulta = actividadRepositorioConsulta;
        this.estadoActividadRepositorioConsulta = estadoActividadRepositorioConsulta;
        this.autorizacionServicio = autorizacionServicio;
    }

    public UUID ejecutar(UUID identificadorEjecucion) {
        autorizacionServicio.validarAccesoAEjecucionActividad(identificadorEjecucion);
        var ejecucion = validarSiExisteEjecucion(identificadorEjecucion);

        validarSiEjecucionActividadEstaPendiente(ejecucion.getEstado());

        var estado = validarSiExisteEstado(EN_CURSO);

        ejecucion.actualizarHoraInicio(LocalTime.now());
        ejecucion.actualizarEstado(estado);

        actividadRepositorioComando.modificarEjecucion(ejecucion);
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

    private void validarSiEjecucionActividadEstaPendiente(EstadoActividad estado) {
        if (!estado.getNombre().equals(PENDIENTE)) {
            throw new InvalidOperationException(INICIAR_ACTIVIDAD_EN_ESTADO_DIFERENTE_A_PENDIENTE);
        }
    }
}