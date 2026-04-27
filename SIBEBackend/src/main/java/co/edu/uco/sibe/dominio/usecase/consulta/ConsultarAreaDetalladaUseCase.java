package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.AreaDetalladaDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.AREA_NO_ENCONTRADA_CON_ID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class ConsultarAreaDetalladaUseCase {

    private final AreaRepositorioConsulta areaRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    public ConsultarAreaDetalladaUseCase(AreaRepositorioConsulta areaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        this.areaRepositorioConsulta = areaRepositorioConsulta;
        this.autorizacionServicio = autorizacionServicio;
    }

    public AreaDetalladaDTO ejecutar(UUID identificador) {
        autorizacionServicio.validarAccesoAArea(identificador);
        return validarSiExisteArea(identificador);
    }

    private AreaDetalladaDTO validarSiExisteArea(UUID id) {
        var area = areaRepositorioConsulta.consultarDetallePorIdentificador(id);
        if (esNulo(area)) {
            throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(AREA_NO_ENCONTRADA_CON_ID, id));
        }
        return area;
    }
}