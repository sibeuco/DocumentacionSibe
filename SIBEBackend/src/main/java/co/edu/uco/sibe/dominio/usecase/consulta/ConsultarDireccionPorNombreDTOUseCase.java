package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.DireccionDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;

import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.DIRECCION_NO_ENCONTRADA_CON_NOMBRE;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class ConsultarDireccionPorNombreDTOUseCase {
    private final DireccionRepositorioConsulta direccionRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    public ConsultarDireccionPorNombreDTOUseCase(DireccionRepositorioConsulta direccionRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        this.direccionRepositorioConsulta = direccionRepositorioConsulta;
        this.autorizacionServicio = autorizacionServicio;
    }

    public DireccionDTO ejecutar(String nombre) {
        var direccion = validarSiExisteDireccion(nombre);
        autorizacionServicio.validarAccesoADireccion(UUID.fromString(direccion.getIdentificador()));
        return direccion;
    }

    private DireccionDTO validarSiExisteDireccion(String nombre) {
        var direccion = direccionRepositorioConsulta.consultarPorNombreDTO(nombre);
        if (esNulo(direccion)) {
            throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(DIRECCION_NO_ENCONTRADA_CON_NOMBRE, nombre));
        }
        return direccion;
    }
}