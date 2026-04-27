package co.edu.uco.sibe.dominio.puerto.comando;

import co.edu.uco.sibe.dominio.modelo.Participante;
import java.util.UUID;

public interface ParticipanteRepositorioComando {
    UUID guardar(Participante participante);
}