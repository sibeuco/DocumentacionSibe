package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Temporalidad;
import co.edu.uco.sibe.dominio.puerto.comando.TemporalidadRepositorioComando;
import co.edu.uco.sibe.infraestructura.adaptador.dao.TemporalidadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.TemporalidadMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class TemporalidadRepositorioComandoImplementacion implements TemporalidadRepositorioComando {
    private final TemporalidadDAO temporalidadDAO;
    private final TemporalidadMapeador temporalidadMapeador;

    @Override
    public UUID guardar(Temporalidad temporalidad) {
        var entidad = temporalidadMapeador.construirEntidad(temporalidad);

        return this.temporalidadDAO.save(entidad).getIdentificador();
    }
}