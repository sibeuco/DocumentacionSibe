package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.puerto.comando.SubareaRepositorioComando;
import co.edu.uco.sibe.infraestructura.adaptador.dao.ActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.SubareaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.SubareaMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class SubareaRepositorioComandoImplementacion implements SubareaRepositorioComando {
    private final SubareaDAO subareaDAO;
    private final SubareaMapeador subareaMapeador;
    private final ActividadDAO actividadDAO;

    @Override
    public UUID guardar(Subarea subarea) {
        var entidad = subareaMapeador.construirEntidad(subarea);

        return this.subareaDAO.save(entidad).getIdentificador();
    }

    @Override
    public UUID guardarActividad(Actividad actividad, UUID subareaId) {
        var subareaEntidad = subareaDAO.findById(subareaId).orElse(null);
        var actividadEntidad = actividadDAO.findById(actividad.getIdentificador()).orElse(null);

        if (!esNulo(subareaEntidad) && !esNulo(actividadEntidad)) {
            subareaEntidad.getActividades().add(actividadEntidad);

            return subareaDAO.save(subareaEntidad).getIdentificador();
        }

        return subareaId;
    }

    @Override
    public void eliminarActividad(Actividad actividad, Subarea subarea) {
        var subareaEntidad = subareaDAO.findById(subarea.getIdentificador()).orElse(null);

        if (!esNulo(subareaEntidad)) {
            subareaEntidad.getActividades().removeIf(a -> a.getIdentificador().equals(actividad.getIdentificador()));
            subareaDAO.save(subareaEntidad);
        }
    }
}