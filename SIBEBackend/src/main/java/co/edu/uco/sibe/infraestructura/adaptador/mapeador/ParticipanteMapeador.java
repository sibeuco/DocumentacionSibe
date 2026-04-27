package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.modelo.Participante;
import co.edu.uco.sibe.dominio.modelo.ParticipanteEmpleado;
import co.edu.uco.sibe.dominio.modelo.ParticipanteEstudiante;
import co.edu.uco.sibe.dominio.modelo.ParticipanteExterno;
import co.edu.uco.sibe.infraestructura.adaptador.dao.InternoDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.MiembroDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.ERROR_DE_INTEGRIDAD_CON_CONSULTA_DEL_MIEMBRO;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class ParticipanteMapeador {
    private final MiembroMapeador miembroMapeador;
    private final InternoCiudadResidenciaMapeador internoCiudadResidenciaMapeador;
    private final EmpleadoRelacionLaboralMapeador empleadoRelacionLaboralMapeador;
    private final EmpleadoCentroCostosMapeador empleadoCentroCostosMapeador;
    private final MiembroDAO miembroDAO;
    private final InternoDAO internoDAO;

    public ParticipanteEntidad construirEntidad(Participante dominio) {
        if (esNulo(dominio)) {
            return null;
        }

        var miembroDominio = dominio.getMiembro();

        if (dominio instanceof ParticipanteExterno e) {
            var miembroEntidadOpt = miembroDAO.findById(miembroDominio.getIdentificador());

            MiembroEntidad miembroEntidad;
            if (miembroEntidadOpt.isEmpty()) {
                miembroEntidad = new ExternoEntidad(
                        miembroDominio.getIdentificador(),
                        miembroDominio.getNombreCompleto(),
                        miembroDominio.getNumeroIdentificacion()
                );
                miembroEntidad = miembroDAO.save(miembroEntidad);
            } else {
                miembroEntidad = miembroEntidadOpt.get();
            }

            return new ParticipanteExternoEntidad(
                    dominio.getIdentificador(),
                    miembroEntidad
            );
        }

        var internoEntidad = internoDAO.findById(miembroDominio.getIdentificador())
                .orElseThrow(() -> new EntityNotFoundException(obtenerMensajeConParametro(ERROR_DE_INTEGRIDAD_CON_CONSULTA_DEL_MIEMBRO, miembroDominio.getIdentificador())));

        var miembroEntidad = (MiembroEntidad) internoEntidad;

        if (dominio instanceof ParticipanteEstudiante e) {
            return new ParticipanteEstudianteEntidad(
                    dominio.getIdentificador(),
                    miembroEntidad,
                    internoEntidad.getCiudadResidencia(),
                    e.getIdCarnet(),
                    e.getSexo(),
                    e.getEstadoCivil(),
                    e.getProgramaAcademico(),
                    e.getFacultad(),
                    e.getAnnoIngreso(),
                    e.getSemestreActual(),
                    e.getCreditosAprobados(),
                    e.getPromedioGeneral(),
                    e.getEstadoAcademico(),
                    e.getModalidadEstudio(),
                    e.getTiempoLlegadaUniversidad(),
                    e.getMedioTransporte()
            );
        }

        if (dominio instanceof ParticipanteEmpleado e) {
            var empleadoEntidad = (EmpleadoEntidad) miembroEntidad;

            return new ParticipanteEmpleadoEntidad(
                    dominio.getIdentificador(),
                    miembroEntidad,
                    internoEntidad.getCiudadResidencia(),
                    e.getIdCarnet(),
                    e.getSexo(),
                    empleadoEntidad.getRelacionLaboral(),
                    empleadoEntidad.getCentroCostos()
            );
        }

        return new ParticipanteEntidad(dominio.getIdentificador(), miembroEntidad);
    }

    public Participante construirModelo(ParticipanteEntidad entidad) {
        if (esNulo(entidad)) {
            return null;
        }

        var miembro = miembroMapeador.construirModelo(entidad.getMiembro());

        if (entidad instanceof ParticipanteEstudianteEntidad e) {
            var interno = (ParticipanteInternoEntidad) e;
            return ParticipanteEstudiante.construir(
                    e.getIdentificador(),
                    miembro,
                    internoCiudadResidenciaMapeador.construirModelo(interno.getCiudadResidencia()),
                    interno.getIdCarnet(),
                    interno.getSexo(),
                    e.getEstadoCivil(),
                    e.getProgramaAcademico(),
                    e.getFacultad(),
                    e.getAnnoIngreso(),
                    e.getSemestreActual(),
                    e.getCreditosAprobados(),
                    e.getPromedioGeneral(),
                    e.getEstadoAcademico(),
                    e.getModalidadEstudio(),
                    e.getTiempoLlegadaUniversidad(),
                    e.getMedioTransporte()
            );
        }

        if (entidad instanceof ParticipanteEmpleadoEntidad e) {
            var interno = (ParticipanteInternoEntidad) e;
            return ParticipanteEmpleado.construir(
                    e.getIdentificador(),
                    miembro,
                    internoCiudadResidenciaMapeador.construirModelo(interno.getCiudadResidencia()),
                    interno.getIdCarnet(),
                    interno.getSexo(),
                    empleadoRelacionLaboralMapeador.construirModelo(e.getRelacionLaboral()),
                    empleadoCentroCostosMapeador.construirModelo(e.getCentroCostos())
            );
        }

        if (entidad instanceof ParticipanteExternoEntidad e) {
            return ParticipanteExterno.construir(e.getIdentificador(), miembro);
        }

        return Participante.construir(entidad.getIdentificador(), miembro);
    }
}