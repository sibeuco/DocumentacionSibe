package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.DireccionDetalladaDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.DIRECCION_NO_ENCONTRADA_CON_ID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class ConsultarDireccionDetalladaUseCase {

    private final DireccionRepositorioConsulta direccionRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    public ConsultarDireccionDetalladaUseCase(DireccionRepositorioConsulta direccionRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        this.direccionRepositorioConsulta = direccionRepositorioConsulta;
        this.autorizacionServicio = autorizacionServicio;
    }

    public DireccionDetalladaDTO ejecutar(UUID identificador) {
        autorizacionServicio.validarAccesoADireccion(identificador);
        return validarSiExisteDireccion(identificador);
    }

    private DireccionDetalladaDTO validarSiExisteDireccion(UUID id) {
        var direccion = direccionRepositorioConsulta.consultarDetallePorIdentificador(id);
        if (esNulo(direccion)) {
            throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(DIRECCION_NO_ENCONTRADA_CON_ID, id));
        }
        return direccion;
    }
}