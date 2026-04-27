package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.modelo.Participante;
import co.edu.uco.sibe.dominio.puerto.consulta.ParticipanteRepositorioConsulta;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EjecucionActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.ParticipanteDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ActividadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EjecucionActividadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.ParticipanteMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class ParticipanteRepositorioConsultaImplementacion implements ParticipanteRepositorioConsulta {
    private final ParticipanteDAO participanteDAO;
    private final ParticipanteMapeador participanteMapeador;
    private final EjecucionActividadDAO ejecucionActividadDAO;

    @Override
    public Participante consultarPorIdentificador(UUID identificador) {
        var entidad = participanteDAO.findById(identificador).orElse(null);

        if (esNulo(entidad)) {
            return null;
        }

        return participanteMapeador.construirModelo(entidad);
    }

    @Override
    public Participante consultarPorDocumentoMiembro(String documento) {
        var entidad = participanteDAO.findByMiembroNumeroIdentificacion(documento);

        if (esNulo(entidad)) {
            return null;
        }

        return participanteMapeador.construirModelo(entidad);
    }

    @Override
    public Participante consultarPorDocumentoYSemestre(String documento, UUID ejecucionActividad) {
        var semestre = ejecucionActividadDAO.findById(ejecucionActividad)
                .map(EjecucionActividadEntidad::getActividad)
                .map(ActividadEntidad::getSemestre)
                .orElse(null);

        if (esNulo(semestre)) {
            return null;
        }

        var entidad = participanteDAO.findByDocumentoAndSemestre(documento, semestre).orElse(null);

        if (esNulo(entidad)) {
            return null;
        }

        return participanteMapeador.construirModelo(entidad);
    }
}