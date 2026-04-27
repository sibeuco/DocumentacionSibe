package co.edu.uco.sibe.dominio.puerto.consulta;

import java.util.UUID;

public interface UsuarioOrganizacionRepositorioConsulta {
    long contarPorDireccion(UUID direccionId);

    long contarPorArea(UUID areaId);

    long contarPorSubarea(UUID subareaId);

    UUID consultarAreaIdPorUsuarioId(UUID usuarioId);

    UUID consultarSubareaIdPorUsuarioId(UUID usuarioId);
}