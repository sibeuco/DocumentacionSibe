package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.dominio.puerto.comando.ActividadRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotoresFabrica;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.service.VincularActividadConAreaService;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.NUMERO_CERO_DOS;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.NUMERO_CERO_UNO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilFecha.obtenerFechaActual;

public class GuardarActividadUseCase {
    private final ActividadRepositorioComando actividadRepositorioComando;
    private final ActividadRepositorioConsulta actividadRepositorioConsulta;
    private final VincularActividadConAreaService vincularActividadConAreaService;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    public GuardarActividadUseCase(ActividadRepositorioComando actividadRepositorioComando,
            ActividadRepositorioConsulta actividadRepositorioConsulta,
            VincularActividadConAreaService vincularActividadConAreaService,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        this.actividadRepositorioComando = actividadRepositorioComando;
        this.actividadRepositorioConsulta = actividadRepositorioConsulta;
        this.vincularActividadConAreaService = vincularActividadConAreaService;
        this.autorizacionServicio = autorizacionServicio;
    }

    public UUID ejecutar(Actividad actividad, List<EjecucionActividad> ejecucionesActividad, UUID area,
            TipoArea tipoArea) {
        validarAccesoPorTipoArea(area, tipoArea);
        MotoresFabrica.MOTOR_ACTIVIDAD.ejecutar(actividad, TipoOperacion.CREAR);

        validarSiExisteActividadConNombreEnElSemestre(actividad.getNombre(), actividad.getSemestre(), area, tipoArea);

        var identificador = this.actividadRepositorioComando.guardar(actividad);

        this.vincularActividadConAreaService.ejecutar(actividad, area, tipoArea);

        ejecucionesActividad.forEach(ejecucionActividad -> {
            MotoresFabrica.MOTOR_ESTADO_ACTIVIDAD.ejecutar(ejecucionActividad.getEstado(), TipoOperacion.CREAR);
            MotoresFabrica.MOTOR_EJECUCION_ACTIVIDAD.ejecutar(ejecucionActividad, TipoOperacion.CREAR);

            validarSiFechaProgramadaEsAnteriorAFechaActual(ejecucionActividad.getFechaProgramada());
            validarSiFechaProgramadaPerteneceASemestreDeLaActividad(ejecucionActividad.getFechaProgramada(),
                    actividad.getSemestre());

            actividadRepositorioComando.guardarEjecucion(ejecucionActividad);
        });

        return identificador;
    }

    private void validarAccesoPorTipoArea(UUID area, TipoArea tipoArea) {
        switch (tipoArea) {
            case DIRECCION -> autorizacionServicio.validarAccesoADireccion(area);
            case AREA -> autorizacionServicio.validarAccesoAArea(area);
            case SUBAREA -> autorizacionServicio.validarAccesoASubarea(area);
        }
    }

    private void validarSiExisteActividadConNombreEnElSemestre(String nombre, String semestre, UUID area, TipoArea tipoArea) {
        if (this.actividadRepositorioConsulta.existeActividadConNombreEnSemestreYArea(nombre, semestre, area, tipoArea, null)) {
            throw new ValorDuplicadoExcepcion(ACTIVIDAD_EXISTENTE_DURANTE_SEMESTRE_ACTUAL);
        }
    }

    private void validarSiFechaProgramadaEsAnteriorAFechaActual(LocalDate fechaProgramada) {
        if (fechaProgramada.isBefore(obtenerFechaActual())) {
            throw new ValorInvalidoExcepcion(FECHA_PROGRAMADA_NO_PUEDE_SER_ANTERIOR_A_FECHA_ACTUAL);
        }
    }

    private void validarSiFechaProgramadaPerteneceASemestreDeLaActividad(LocalDate fechaProgramada, String semestre) {
        var anioSemestre = Integer.parseInt(semestre.substring(0, 4));
        var anioFecha = fechaProgramada.getYear();
        var mesFecha = fechaProgramada.getMonthValue();

        if (semestre.endsWith(NUMERO_CERO_UNO) && !validarPrimerSemestre(anioSemestre, anioFecha, mesFecha)) {
            throw new ValorInvalidoExcepcion(FECHA_PROGRAMADA_NO_PERTENECE_AL_SEMESTRE_DE_LA_ACTIVIDAD);
        }

        if (semestre.endsWith(NUMERO_CERO_DOS) && !validarSegundoSemestre(anioSemestre, anioFecha, mesFecha)) {
            throw new ValorInvalidoExcepcion(FECHA_PROGRAMADA_NO_PERTENECE_AL_SEMESTRE_DE_LA_ACTIVIDAD);
        }
    }

    private static boolean validarPrimerSemestre(int anioSemestre, int anioFecha, int mesFecha) {
        return anioSemestre == anioFecha && mesFecha <= 6;
    }

    private static boolean validarSegundoSemestre(int anioSemestre, int anioFecha, int mesFecha) {
        return anioSemestre == anioFecha && mesFecha >= 7;
    }
}