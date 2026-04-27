package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.UsuarioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UsuarioDAO extends JpaRepository<UsuarioEntidad, UUID> {
    UsuarioEntidad findByCorreo(String correo);
}