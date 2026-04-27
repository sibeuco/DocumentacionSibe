package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.SubareaDetalladaDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.SUBAREA_NO_ENCONTRADA_CON_ID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class ConsultarSubareaDetalladaUseCase {

    private final SubareaRepositorioConsulta subareaRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    public ConsultarSubareaDetalladaUseCase(SubareaRepositorioConsulta subareaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        this.subareaRepositorioConsulta = subareaRepositorioConsulta;
        this.autorizacionServicio = autorizacionServicio;
    }

    public SubareaDetalladaDTO ejecutar(UUID identificador) {
        autorizacionServicio.validarAccesoASubarea(identificador);
        return validarSiExisteSubarea(identificador);
    }

    private SubareaDetalladaDTO validarSiExisteSubarea(UUID id) {
        var subarea = subareaRepositorioConsulta.consultarDetallePorIdentificador(id);
        if (esNulo(subarea)) {
            throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(SUBAREA_NO_ENCONTRADA_CON_ID, id));
        }
        return subarea;
    }
}