package co.edu.uco.sibe.dominio.puerto.comando;

import co.edu.uco.sibe.dominio.modelo.Indicador;
import java.util.UUID;

public interface IndicadorRepositorioComando {
    UUID guardar(Indicador indicador);

    UUID modificar(Indicador indicador, UUID identificador);
}