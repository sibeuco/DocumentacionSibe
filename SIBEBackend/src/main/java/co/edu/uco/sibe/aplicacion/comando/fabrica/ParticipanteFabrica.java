package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.ParticipanteComando;
import co.edu.uco.sibe.dominio.modelo.*;
import co.edu.uco.sibe.dominio.puerto.consulta.MiembroRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.ParticipanteRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.MIEMBRO_NO_ENCONTRADO_CON_DOCUMENTO;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class ParticipanteFabrica {
    private final MiembroRepositorioConsulta miembroRepositorioConsulta;
    private final ParticipanteRepositorioConsulta participanteRepositorioConsulta;

    public List<Participante> construirParticipantes(List<ParticipanteComando> comandos, UUID ejecucionActividad) {
        return comandos.stream().map(participanteComando -> this.construir(participanteComando, ejecucionActividad)).toList();
    }

    public Participante construir(ParticipanteComando comando, UUID ejecucionActividad) {
        var participanteExistente = participanteRepositorioConsulta.consultarPorDocumentoYSemestre(comando.getDocumentoIdentificacion(), ejecucionActividad);

        if(!esNulo(participanteExistente)) {
            return participanteExistente;
        }

        var identificadorParticipante = generar(uuid -> !esNulo(this.participanteRepositorioConsulta.consultarPorIdentificador(uuid)));

        if(esNulo(comando.getIdentificador())) {
            var identificadorExterno = generar(uuid -> !esNulo(this.miembroRepositorioConsulta.consultarPorIdentificador(uuid)));

            var externo = Externo.construir(identificadorExterno, comando.getNombreCompleto(), comando.getDocumentoIdentificacion());

            return ParticipanteExterno.construir(identificadorParticipante, externo);
        }

        var miembro = miembroRepositorioConsulta.consultarPorIdentificacion(comando.getDocumentoIdentificacion());

        if (esNulo(miembro)) {
            throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(MIEMBRO_NO_ENCONTRADO_CON_DOCUMENTO, comando.getDocumentoIdentificacion()));
        }

        if (miembro instanceof Estudiante) {
            return construirParticipanteEstudiante(identificadorParticipante, (Estudiante) miembro);
        }

        if (miembro instanceof Empleado) {
            return construirParticipanteEmpleado(identificadorParticipante, (Empleado) miembro);
        }

        return Participante.construir(identificadorParticipante, miembro);
    }

    private ParticipanteEstudiante construirParticipanteEstudiante(UUID identificador, Estudiante estudiante) {
        return ParticipanteEstudiante.construir(
                identificador,
                estudiante,
                estudiante.getCiudadResidencia(),
                estudiante.getIdCarnet(),
                estudiante.getSexo(),
                estudiante.getEstadoCivil(),
                estudiante.getProgramaAcademico(),
                estudiante.getFacultad(),
                estudiante.getAnnoIngreso(),
                estudiante.getSemestreActual(),
                estudiante.getCreditosAprobados(),
                estudiante.getPromedioGeneral(),
                estudiante.getEstadoAcademico(),
                estudiante.getModalidadEstudio(),
                estudiante.getTiempoLlegadaUniversidad(),
                estudiante.getMedioTransporte()
        );
    }

    private ParticipanteEmpleado construirParticipanteEmpleado(UUID identificador, Empleado empleado) {
        return ParticipanteEmpleado.construir(
                identificador,
                empleado,
                empleado.getCiudadResidencia(),
                empleado.getIdCarnet(),
                empleado.getSexo(),
                empleado.getRelacionLaboral(),
                empleado.getCentroCostos()
        );
    }
}