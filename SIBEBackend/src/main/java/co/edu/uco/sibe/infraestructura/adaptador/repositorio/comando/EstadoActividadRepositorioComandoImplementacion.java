package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.dominio.puerto.comando.EstadoActividadRepositorioComando;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EstadoActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.EstadoActividadMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class EstadoActividadRepositorioComandoImplementacion implements EstadoActividadRepositorioComando {
    private final EstadoActividadDAO estadoActividadDAO;
    private final EstadoActividadMapeador estadoActividadMapeador;

    @Override
    public UUID guardar(EstadoActividad estadoActividad) {
        var entidad = this.estadoActividadMapeador.construirEntidad(estadoActividad);

        return this.estadoActividadDAO.save(entidad).getIdentificador();
    }
}