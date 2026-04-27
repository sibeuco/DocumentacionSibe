package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.puerto.comando.DireccionRepositorioComando;
import co.edu.uco.sibe.infraestructura.adaptador.dao.ActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.DireccionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.DireccionMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class DireccionRepositorioComandoImplementacion implements DireccionRepositorioComando {
    private final DireccionDAO direccionDAO;
    private final DireccionMapeador direccionMapeador;
    private final ActividadDAO actividadDAO;

    @Override
    public UUID guardar(Direccion direccion) {
        var entidad = direccionMapeador.construirEntidad(direccion);
        return this.direccionDAO.save(entidad).getIdentificador();
    }

    @Override
    public UUID guardarActividad(Actividad actividad, UUID direccionId) {
        var direccionEntidad = direccionDAO.findById(direccionId).orElse(null);
        var actividadEntidad = actividadDAO.findById(actividad.getIdentificador()).orElse(null);

        if (!esNulo(direccionEntidad) && !esNulo(actividadEntidad)) {
            direccionEntidad.getActividades().add(actividadEntidad);

            return direccionDAO.save(direccionEntidad).getIdentificador();
        }

        return direccionId;
    }

    @Override
    public void eliminarActividad(Actividad actividad, Direccion direccion) {
        var direccionEntidad = direccionDAO.findById(direccion.getIdentificador()).orElse(null);

        if (!esNulo(direccionEntidad)) {
            direccionEntidad.getActividades().removeIf(a -> a.getIdentificador().equals(actividad.getIdentificador()));
            direccionDAO.save(direccionEntidad);
        }
    }
}