package co.edu.uco.sibe.infraestructura.adaptador.dao;

import co.edu.uco.sibe.infraestructura.adaptador.entidad.EmpleadoCentroCostosEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EmpleadoCentroCostosDAO extends JpaRepository<EmpleadoCentroCostosEntidad, UUID> { }