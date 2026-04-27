package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.modelo.Estudiante;
import co.edu.uco.sibe.dominio.puerto.consulta.EstudianteRepositorioConsulta;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EstudianteDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.EstudianteMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class EstudianteRepositorioConsultaImplementacion implements EstudianteRepositorioConsulta {
    private final EstudianteDAO estudianteDAO;
    private final EstudianteMapeador estudianteMapeador;

    @Override
    public Estudiante consultarPorIdentificador(UUID identificador) {
        var entidad = this.estudianteDAO.findById(identificador).orElse(null);

        if(esNulo(entidad)) {
            return null;
        }

        return this.estudianteMapeador.construirModelo(entidad);
    }

    @Override
    public Estudiante consultarPorIdentificacion(String identificacion) {
        var entidad = this.estudianteDAO.findFirstByNumeroIdentificacion(identificacion);

        if(esNulo(entidad)) {
            return null;
        }

        return this.estudianteMapeador.construirModelo(entidad);
    }
}