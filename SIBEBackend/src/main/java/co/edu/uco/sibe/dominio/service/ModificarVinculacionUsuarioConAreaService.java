package co.edu.uco.sibe.dominio.service;

import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.puerto.comando.UsuarioOrganizacionComando;
import java.util.UUID;

public class ModificarVinculacionUsuarioConAreaService {
    private final UsuarioOrganizacionComando usuarioOrganizacionComando;

    public ModificarVinculacionUsuarioConAreaService(UsuarioOrganizacionComando usuarioOrganizacionComando) {
        this.usuarioOrganizacionComando = usuarioOrganizacionComando;
    }

    public UUID ejecutar(UUID usuario, UUID area, TipoArea tipoArea) {
        switch (tipoArea) {
            case DIRECCION -> {
                return usuarioOrganizacionComando.cambiarVinculacionUsuarioConDireccion(usuario, area);
            }
            case AREA -> {
                return usuarioOrganizacionComando.cambiarVinculacionUsuarioConArea(usuario, area);
            }
            case SUBAREA -> {
                return usuarioOrganizacionComando.cambiarVinculacionUsuarioConSubarea(usuario, area);
            }
            default -> {
                return null;
            }
        }
    }
}