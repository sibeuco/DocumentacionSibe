package co.edu.uco.sibe.dominio.service;

import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.puerto.comando.UsuarioOrganizacionComando;
import java.util.UUID;

public class VincularUsuarioConAreaService {
    private final UsuarioOrganizacionComando usuarioOrganizacionComando;

    public VincularUsuarioConAreaService(UsuarioOrganizacionComando usuarioOrganizacionComando) {
        this.usuarioOrganizacionComando = usuarioOrganizacionComando;
    }

    public UUID ejecutar(UUID usuario, UUID area, TipoArea tipoArea) {
        switch (tipoArea) {
            case DIRECCION -> {
                return usuarioOrganizacionComando.vincularUsuarioConDireccion(usuario, area);
            }
            case AREA -> {
                return usuarioOrganizacionComando.vincularUsuarioConArea(usuario, area);
            }
            case SUBAREA -> {
                return usuarioOrganizacionComando.vincularUsuarioConSubarea(usuario, area);
            }
            default -> {
                return null;
            }
        }
    }
}