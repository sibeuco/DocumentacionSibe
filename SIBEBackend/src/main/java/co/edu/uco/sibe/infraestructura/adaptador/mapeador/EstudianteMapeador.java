package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.modelo.Estudiante;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EstudianteEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EstudianteMapeador {
    private final InternoCiudadResidenciaMapeador internoCiudadResidenciaMapeador;

    public Estudiante construirModelo(EstudianteEntidad estudianteEntidad) {
        return Estudiante.construir(
                estudianteEntidad.getIdentificador(),
                estudianteEntidad.getNombreCompleto(),
                estudianteEntidad.getNumeroIdentificacion(),
                this.internoCiudadResidenciaMapeador.construirModelo(estudianteEntidad.getCiudadResidencia()),
                estudianteEntidad.getIdCarnet(),
                estudianteEntidad.getSexo(),
                estudianteEntidad.getFechaNacimiento(),
                estudianteEntidad.getNacionalidad(),
                estudianteEntidad.getEstadoCivil(),
                estudianteEntidad.getCorreoPersonal(),
                estudianteEntidad.getCorreoInstitucional(),
                estudianteEntidad.getProgramaAcademico(),
                estudianteEntidad.getFacultad(),
                estudianteEntidad.getAnnoIngreso(),
                estudianteEntidad.getSemestreActual(),
                estudianteEntidad.getCreditosAprobados(),
                estudianteEntidad.getPromedioGeneral(),
                estudianteEntidad.getEstadoAcademico(),
                estudianteEntidad.getModalidadEstudio(),
                estudianteEntidad.getTiempoLlegadaUniversidad(),
                estudianteEntidad.getMedioTransporte()
        );
    }

    public EstudianteEntidad construirEntidad(Estudiante estudiante) {
        return new EstudianteEntidad(
                estudiante.getIdentificador(),
                estudiante.getNombreCompleto(),
                estudiante.getNumeroIdentificacion(),
                this.internoCiudadResidenciaMapeador.construirEntidad(estudiante.getCiudadResidencia()),
                estudiante.getIdCarnet(),
                estudiante.getSexo(),
                estudiante.getFechaNacimiento(),
                estudiante.getNacionalidad(),
                estudiante.getEstadoCivil(),
                estudiante.getCorreoPersonal(),
                estudiante.getCorreoInstitucional(),
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

    public void modificarEntidad(EstudianteEntidad entidad, Estudiante estudiante) {
        entidad.setNombreCompleto(estudiante.getNombreCompleto());
        entidad.setNumeroIdentificacion(estudiante.getNumeroIdentificacion());
        this.internoCiudadResidenciaMapeador.modificarEntidad(entidad.getCiudadResidencia(), estudiante.getCiudadResidencia());
        entidad.setIdCarnet(estudiante.getIdCarnet());
        entidad.setSexo(estudiante.getSexo());
        entidad.setFechaNacimiento(estudiante.getFechaNacimiento());
        entidad.setNacionalidad(estudiante.getNacionalidad());
        entidad.setEstadoCivil(estudiante.getEstadoCivil());
        entidad.setCorreoPersonal(estudiante.getCorreoPersonal());
        entidad.setCorreoInstitucional(estudiante.getCorreoInstitucional());
        entidad.setProgramaAcademico(estudiante.getProgramaAcademico());
        entidad.setFacultad(estudiante.getFacultad());
        entidad.setAnnoIngreso(estudiante.getAnnoIngreso());
        entidad.setSemestreActual(estudiante.getSemestreActual());
        entidad.setCreditosAprobados(estudiante.getCreditosAprobados());
        entidad.setPromedioGeneral(estudiante.getPromedioGeneral());
        entidad.setEstadoAcademico(estudiante.getEstadoAcademico());
        entidad.setModalidadEstudio(estudiante.getModalidadEstudio());
        entidad.setTiempoLlegadaUniversidad(estudiante.getTiempoLlegadaUniversidad());
        entidad.setMedioTransporte(estudiante.getMedioTransporte());
    }
}