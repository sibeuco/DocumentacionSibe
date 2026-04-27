package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.modelo.Participante;
import java.util.UUID;

public interface ParticipanteRepositorioConsulta {
    Participante consultarPorIdentificador(UUID identificador);
    Participante consultarPorDocumentoMiembro(String documento);
    Participante consultarPorDocumentoYSemestre(String documento, UUID ejecucionActividad);
}