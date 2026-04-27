package co.edu.uco.sibe.dominio.service;

import co.edu.uco.sibe.dominio.modelo.Participante;
import co.edu.uco.sibe.dominio.puerto.comando.ParticipanteRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.ParticipanteRepositorioConsulta;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class RegistrarParticipanteService {
    private final ParticipanteRepositorioConsulta participanteRepositorioConsulta;
    private final ParticipanteRepositorioComando participanteRepositorioComando;

    public RegistrarParticipanteService(ParticipanteRepositorioConsulta participanteRepositorioConsulta, ParticipanteRepositorioComando participanteRepositorioComando) {
        this.participanteRepositorioConsulta = participanteRepositorioConsulta;
        this.participanteRepositorioComando = participanteRepositorioComando;
    }

    public Participante ejecutar(Participante participante) {
        var participanteExistente = participanteRepositorioConsulta.consultarPorDocumentoMiembro(participante.getMiembro().getNumeroIdentificacion());

        if (esNulo(participanteExistente)) {
            participanteRepositorioComando.guardar(participante);

            return participante;
        }

        return participanteExistente;
    }
}