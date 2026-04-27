package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.modelo.Identificacion;
import java.util.UUID;

public interface IdentificacionRepositorioConsulta {
    Identificacion consultarPorIdentificador(UUID identificador);
    Identificacion consultarPorNumeroIdentificacion(String numeroIdentificacion);
}