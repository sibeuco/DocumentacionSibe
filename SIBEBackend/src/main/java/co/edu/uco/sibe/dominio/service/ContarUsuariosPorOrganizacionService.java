package co.edu.uco.sibe.dominio.service;

import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.puerto.consulta.UsuarioOrganizacionRepositorioConsulta;

public class ContarUsuariosPorOrganizacionService {

    private final UsuarioOrganizacionRepositorioConsulta usuarioOrganizacionRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    public ContarUsuariosPorOrganizacionService(
            UsuarioOrganizacionRepositorioConsulta usuarioOrganizacionRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        this.usuarioOrganizacionRepositorioConsulta = usuarioOrganizacionRepositorioConsulta;
        this.autorizacionServicio = autorizacionServicio;
    }

    public long contarUsuariosPorDireccion(Direccion direccion) {
        autorizacionServicio.validarAccesoADireccion(direccion.getIdentificador());
        long total = usuarioOrganizacionRepositorioConsulta.contarPorDireccion(direccion.getIdentificador());
        total += direccion.getAreas().stream()
                .mapToLong(this::contarUsuariosPorAreaSinValidacion)
                .sum();
        return total;
    }

    public long contarUsuariosPorArea(Area area) {
        autorizacionServicio.validarAccesoAArea(area.getIdentificador());
        return contarUsuariosPorAreaSinValidacion(area);
    }

    public long contarUsuariosPorSubarea(Subarea subarea) {
        autorizacionServicio.validarAccesoASubarea(subarea.getIdentificador());
        return usuarioOrganizacionRepositorioConsulta.contarPorSubarea(subarea.getIdentificador());
    }

    private long contarUsuariosPorAreaSinValidacion(Area area) {
        long total = usuarioOrganizacionRepositorioConsulta.contarPorArea(area.getIdentificador());
        total += area.getSubareas().stream()
                .mapToLong(
                        subarea -> usuarioOrganizacionRepositorioConsulta.contarPorSubarea(subarea.getIdentificador()))
                .sum();
        return total;
    }
}