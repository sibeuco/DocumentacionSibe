package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.AreaDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;

import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.AREA_NO_ENCONTRADA_CON_NOMBRE;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class ConsultarAreaPorNombreDTOUseCase {
    private final AreaRepositorioConsulta areaRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    public ConsultarAreaPorNombreDTOUseCase(AreaRepositorioConsulta areaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        this.areaRepositorioConsulta = areaRepositorioConsulta;
        this.autorizacionServicio = autorizacionServicio;
    }

    public AreaDTO ejecutar(String nombre) {
        var area = validarSiExisteArea(nombre);
        autorizacionServicio.validarAccesoAArea(UUID.fromString(area.getIdentificador()));
        return area;
    }

    private AreaDTO validarSiExisteArea(String nombre) {
        var area = areaRepositorioConsulta.consultarPorNombreDTO(nombre);
        if (esNulo(area)) {
            throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(AREA_NO_ENCONTRADA_CON_NOMBRE, nombre));
        }
        return area;
    }
}