package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.EmpleadoRelacionLaboralEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EmpleadoRelacionLaboralDAO extends JpaRepository<EmpleadoRelacionLaboralEntidad, UUID> { }