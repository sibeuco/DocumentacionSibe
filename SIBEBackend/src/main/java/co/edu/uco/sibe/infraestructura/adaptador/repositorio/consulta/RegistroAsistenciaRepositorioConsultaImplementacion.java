package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.modelo.RegistroAsistencia;
import co.edu.uco.sibe.dominio.puerto.consulta.RegistroAsistenciaRepositorioConsulta;
import co.edu.uco.sibe.infraestructura.adaptador.dao.RegistroAsistenciaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.RegistroAsistenciaMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class RegistroAsistenciaRepositorioConsultaImplementacion implements RegistroAsistenciaRepositorioConsulta {
    private final RegistroAsistenciaDAO registroAsistenciaDAO;
    private final RegistroAsistenciaMapeador registroAsistenciaMapeador;

    @Override
    public RegistroAsistencia consultarPorIdentificador(UUID identificador) {
        var entidad = this.registroAsistenciaDAO.findById(identificador).orElse(null);

        if(esNulo(entidad)) {
            return null;
        }

        return this.registroAsistenciaMapeador.construirModelo(entidad);
    }
}