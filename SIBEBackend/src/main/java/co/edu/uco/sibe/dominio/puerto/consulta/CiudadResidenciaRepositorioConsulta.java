package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.modelo.CiudadResidencia;
import java.util.UUID;

public interface CiudadResidenciaRepositorioConsulta {
    CiudadResidencia consultarPorIdentificador(UUID identificador);
    CiudadResidencia consultarPorDescripcion(String descripcion);
}