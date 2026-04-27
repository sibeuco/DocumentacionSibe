package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Estudiante;
import co.edu.uco.sibe.dominio.puerto.comando.EstudianteRepositorioComando;
import co.edu.uco.sibe.infraestructura.adaptador.dao.CiudadResidenciaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EstudianteDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.EstudianteMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class EstudianteRepositorioComandoImplementacion implements EstudianteRepositorioComando {
    private final EstudianteDAO estudianteDAO;
    private final EstudianteMapeador estudianteMapeador;
    private final CiudadResidenciaDAO ciudadResidenciaDAO;

    @Override
    public UUID guardar(Estudiante estudiante) {
        var entidad = estudianteMapeador.construirEntidad(estudiante);
        entidad.setEstaActivo(true);

        if (esNulo(this.ciudadResidenciaDAO.findById(entidad.getCiudadResidencia().getCiudadResidencia().getIdentificador()).orElse(null))) {
            this.ciudadResidenciaDAO.save(entidad.getCiudadResidencia().getCiudadResidencia());
        }

        return this.estudianteDAO.save(entidad).getIdentificador();
    }

    @Override
    public UUID modificar(Estudiante estudiante, UUID identificador) {
        var entidad = estudianteDAO.findById(identificador).orElse(null);

        assert !esNulo(entidad);
        estudianteMapeador.modificarEntidad(entidad, estudiante);
        entidad.setEstaActivo(true);

        return this.estudianteDAO.save(entidad).getIdentificador();
    }

    @Override
    public void desactivarTodos() {
        this.estudianteDAO.desactivarTodos();
    }
}