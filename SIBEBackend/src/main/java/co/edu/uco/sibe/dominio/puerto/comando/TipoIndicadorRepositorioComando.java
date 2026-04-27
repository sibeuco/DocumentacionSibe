package co.edu.uco.sibe.dominio.puerto.comando;

import co.edu.uco.sibe.dominio.modelo.TipoIndicador;
import java.util.UUID;

public interface TipoIndicadorRepositorioComando {
    UUID guardar(TipoIndicador tipoIndicador);
}