package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.InternoCiudadResidenciaEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface InternoCiudadResidenciaDAO extends JpaRepository<InternoCiudadResidenciaEntidad, UUID> { }