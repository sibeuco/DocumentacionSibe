package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.RegistroAsistencia;
import co.edu.uco.sibe.dominio.puerto.comando.RegistroAsistenciaRepositorioComando;
import co.edu.uco.sibe.infraestructura.adaptador.dao.RegistroAsistenciaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.RegistroAsistenciaMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class RegistroAsistenciaRepositorioComandoImplementacion implements RegistroAsistenciaRepositorioComando {
    private final RegistroAsistenciaDAO registroAsistenciaDAO;
    private final RegistroAsistenciaMapeador registroAsistenciaMapeador;

    @Override
    public UUID guardar(RegistroAsistencia registroAsistencia) {
        var entidad = registroAsistenciaMapeador.construirEntidad(registroAsistencia);

        return registroAsistenciaDAO.save(entidad).getIdentificador();
    }
}