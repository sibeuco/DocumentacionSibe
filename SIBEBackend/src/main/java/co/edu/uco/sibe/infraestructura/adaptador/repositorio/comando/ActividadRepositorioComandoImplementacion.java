package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.dominio.puerto.comando.ActividadRepositorioComando;
import co.edu.uco.sibe.infraestructura.adaptador.dao.ActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EjecucionActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.ActividadMapeador;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.EjecucionActividadMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class ActividadRepositorioComandoImplementacion implements ActividadRepositorioComando {
    private final ActividadDAO actividadDAO;
    private final ActividadMapeador actividadMapeador;
    private final EjecucionActividadMapeador ejecucionActividadMapeador;
    private final EjecucionActividadDAO ejecucionActividadDAO;

    @Override
    public UUID guardar(Actividad actividad) {
        var entidad = actividadMapeador.construirEntidad(actividad);

        return this.actividadDAO.save(entidad).getIdentificador();
    }

    @Override
    public UUID guardarEjecucion(EjecucionActividad ejecucionActividad) {
        var entidad = ejecucionActividadMapeador.construirEntidad(ejecucionActividad);

        return this.ejecucionActividadDAO.save(entidad).getIdentificador();
    }

    @Override
    public UUID modificar(Actividad actividad) {
        var entidad = actividadDAO.findById(actividad.getIdentificador()).orElse(null);

        assert !esNulo(entidad);
        actividadMapeador.actualizarEntidad(entidad, actividad);

        return this.actividadDAO.save(entidad).getIdentificador();
    }

    @Override
    public void modificarEjecuciones(List<EjecucionActividad> ejecucionesActividad) {
        if (ejecucionesActividad.isEmpty()) {
            return;
        }

        var identificadorActividad = ejecucionesActividad.get(0).getActividad().getIdentificador();

        ejecucionActividadMapeador.actualizarTodos(identificadorActividad, ejecucionesActividad);
    }

    @Override
    public void modificarEjecucion(EjecucionActividad ejecucion) {
        var entidad = ejecucionActividadDAO.findById(ejecucion.getIdentificador()).orElse(null);

        if (!esNulo(entidad)) {
            ejecucionActividadMapeador.actualizarEntidad(entidad, ejecucion);
            ejecucionActividadDAO.save(entidad);
        }
    }
}