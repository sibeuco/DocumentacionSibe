package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.SubareaDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;

import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.SUBAREA_NO_ENCONTRADA_CON_NOMBRE;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class ConsultarSubareaPorNombreDTOUseCase {
    private final SubareaRepositorioConsulta subareaRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    public ConsultarSubareaPorNombreDTOUseCase(SubareaRepositorioConsulta subareaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        this.subareaRepositorioConsulta = subareaRepositorioConsulta;
        this.autorizacionServicio = autorizacionServicio;
    }

    public SubareaDTO ejecutar(String nombre) {
        var subarea = validarSiExisteSubarea(nombre);
        autorizacionServicio.validarAccesoASubarea(UUID.fromString(subarea.getIdentificador()));
        return subarea;
    }

    private SubareaDTO validarSiExisteSubarea(String nombre) {
        var subarea = subareaRepositorioConsulta.consultarPorNombreDTO(nombre);
        if (esNulo(subarea)) {
            throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(SUBAREA_NO_ENCONTRADA_CON_NOMBRE, nombre));
        }
        return subarea;
    }
}