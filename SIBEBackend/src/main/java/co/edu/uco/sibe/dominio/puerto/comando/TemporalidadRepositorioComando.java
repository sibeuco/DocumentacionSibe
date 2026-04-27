package co.edu.uco.sibe.dominio.puerto.comando;

import co.edu.uco.sibe.dominio.modelo.Temporalidad;
import java.util.UUID;

public interface TemporalidadRepositorioComando {
    UUID guardar(Temporalidad temporalidad);
}