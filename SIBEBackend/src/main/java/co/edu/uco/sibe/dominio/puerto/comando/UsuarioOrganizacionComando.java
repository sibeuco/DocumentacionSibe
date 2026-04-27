package co.edu.uco.sibe.dominio.puerto.comando;

import java.util.UUID;

public interface UsuarioOrganizacionComando {
    UUID vincularUsuarioConDireccion(UUID usuario, UUID direccion);

    UUID cambiarVinculacionUsuarioConDireccion(UUID persona, UUID direccion);

    UUID vincularUsuarioConArea(UUID usuario, UUID area);

    UUID cambiarVinculacionUsuarioConArea(UUID persona, UUID area);

    UUID vincularUsuarioConSubarea(UUID usuario, UUID subarea);

    UUID cambiarVinculacionUsuarioConSubarea(UUID persona, UUID subarea);
}