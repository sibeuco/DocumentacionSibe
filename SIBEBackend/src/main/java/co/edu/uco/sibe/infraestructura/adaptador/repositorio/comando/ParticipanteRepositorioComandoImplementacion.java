package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Participante;
import co.edu.uco.sibe.dominio.puerto.comando.ParticipanteRepositorioComando;
import co.edu.uco.sibe.infraestructura.adaptador.dao.ParticipanteDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.ParticipanteMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class ParticipanteRepositorioComandoImplementacion implements ParticipanteRepositorioComando {
    private final ParticipanteDAO participanteDAO;
    private final ParticipanteMapeador participanteMapeador;

    @Override
    public UUID guardar(Participante participante) {
        var entidad = participanteMapeador.construirEntidad(participante);

        return participanteDAO.save(entidad).getIdentificador();
    }
}