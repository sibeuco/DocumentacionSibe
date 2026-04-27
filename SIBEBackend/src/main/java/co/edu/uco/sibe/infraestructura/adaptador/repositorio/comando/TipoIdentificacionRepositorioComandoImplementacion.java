package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.dominio.puerto.comando.TipoIdentificacionRepositorioComando;
import co.edu.uco.sibe.infraestructura.adaptador.dao.TipoIdentificacionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.TipoIdentificacionMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class TipoIdentificacionRepositorioComandoImplementacion implements TipoIdentificacionRepositorioComando {
    private final TipoIdentificacionDAO tipoIdentificacionDAO;
    private final TipoIdentificacionMapeador tipoIdentificacionMapeador;

    @Override
    public UUID guardar(TipoIdentificacion tipoIdentificacion) {
        var entidad = tipoIdentificacionMapeador.construirEntidad(tipoIdentificacion);

        return tipoIdentificacionDAO.save(entidad).getIdentificador();
    }
}