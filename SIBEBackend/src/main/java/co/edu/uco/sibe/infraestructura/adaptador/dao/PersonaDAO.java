package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.IdentificacionEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.PersonaEntidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.UUID;

public interface PersonaDAO extends JpaRepository<PersonaEntidad, UUID> {
    PersonaEntidad findByCorreo(String correo);

    PersonaEntidad findByIdentificacion(IdentificacionEntidad identificacion);

    @Query("SELECT p FROM PersonaEntidad p JOIN UsuarioEntidad u ON u.correo = p.correo " +
           "WHERE u.estaActivo = true AND u.rol.tipoUsuario.nombre = :tipoUsuario")
    Page<PersonaEntidad> findByTipoUsuario(@Param("tipoUsuario") String tipoUsuario, Pageable pageable);

    @Query("SELECT p FROM PersonaEntidad p JOIN UsuarioEntidad u ON u.correo = p.correo " +
           "WHERE u.estaActivo = true AND u.rol.tipoUsuario.nombre <> :tipoUsuario")
    Page<PersonaEntidad> findByTipoUsuarioNot(@Param("tipoUsuario") String tipoUsuario, Pageable pageable);

    @Query("SELECT COUNT(u) FROM UsuarioEntidad u " +
           "WHERE u.estaActivo = true AND u.rol.tipoUsuario.codigo = :codigoTipoUsuario")
    long countActivosPorTipoUsuario(@Param("codigoTipoUsuario") String codigoTipoUsuario);
}