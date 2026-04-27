package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.UsuarioEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.UsuarioOrganizacionEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UsuarioOrganizacionDAO extends JpaRepository<UsuarioOrganizacionEntidad, UUID> {
    UsuarioOrganizacionEntidad findByUsuario(UsuarioEntidad usuario);

    UsuarioOrganizacionEntidad findByUsuarioIdentificador(UUID usuarioId);

    long countByDireccionIdentificador(UUID direccionId);

    long countByAreaIdentificador(UUID areaId);

    long countBySubareaIdentificador(UUID subareaId);
}