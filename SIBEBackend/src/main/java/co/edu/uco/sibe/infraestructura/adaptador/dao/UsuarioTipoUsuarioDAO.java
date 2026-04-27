package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.UsuarioTipoUsuarioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UsuarioTipoUsuarioDAO extends JpaRepository<UsuarioTipoUsuarioEntidad, UUID> { }