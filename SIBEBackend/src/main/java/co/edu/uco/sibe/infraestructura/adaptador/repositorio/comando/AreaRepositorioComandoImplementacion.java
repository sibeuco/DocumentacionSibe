package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.puerto.comando.AreaRepositorioComando;
import co.edu.uco.sibe.infraestructura.adaptador.dao.ActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.AreaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.AreaMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class AreaRepositorioComandoImplementacion implements AreaRepositorioComando {
    private final AreaDAO areaDAO;
    private final AreaMapeador areaMapeador;
    private final ActividadDAO actividadDAO;

    @Override
    public UUID guardar(Area area) {
        var entidad = areaMapeador.construirEntidad(area);
        return this.areaDAO.save(entidad).getIdentificador();
    }

    @Override
    public UUID guardarActividad(Actividad actividad, UUID areaId) {
        var areaEntidad = areaDAO.findById(areaId).orElse(null);
        var actividadEntidad = actividadDAO.findById(actividad.getIdentificador()).orElse(null);

        if (!esNulo(areaEntidad) && !esNulo(actividadEntidad)) {
            areaEntidad.getActividades().add(actividadEntidad);

            return areaDAO.save(areaEntidad).getIdentificador();
        }

        return areaId;
    }

    @Override
    public void eliminarActividad(Actividad actividad, Area area) {
        var areaEntidad = areaDAO.findById(area.getIdentificador()).orElse(null);

        if (!esNulo(areaEntidad)) {
            areaEntidad.getActividades().removeIf(a -> a.getIdentificador().equals(actividad.getIdentificador()));
            areaDAO.save(areaEntidad);
        }
    }
}