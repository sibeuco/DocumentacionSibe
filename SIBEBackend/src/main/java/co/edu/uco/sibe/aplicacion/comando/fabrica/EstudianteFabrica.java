package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.DatosEstudianteComando;
import co.edu.uco.sibe.dominio.modelo.Estudiante;
import co.edu.uco.sibe.dominio.puerto.consulta.EstudianteRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class EstudianteFabrica {
    private final CiudadResidenciaFabrica ciudadResidenciaFabrica;
    private final EstudianteRepositorioConsulta estudianteRepositorioConsulta;
    private static final DateTimeFormatter EXCEL_DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");
    
    public Estudiante construir(DatosEstudianteComando estudianteComando) {
        return Estudiante.construir(
                generar(uuid -> !esNulo(estudianteRepositorioConsulta.consultarPorIdentificador(uuid))),
                estudianteComando.getNombreCompleto(),
                estudianteComando.getNumeroIdentificacion(),
                ciudadResidenciaFabrica.construir(estudianteComando.getMunicipioResidencia()),
                estudianteComando.getIdLecturaCarnetUniversitario(),
                estudianteComando.getSexo(),
                LocalDate.parse(estudianteComando.getFechaNacimiento(), EXCEL_DATE_FORMATTER),
                estudianteComando.getNacionalidad(),
                estudianteComando.getEstadoCivil(),
                estudianteComando.getCorreoPersonal(),
                estudianteComando.getCorreoInstitucional(),
                estudianteComando.getProgramaAcademico(),
                estudianteComando.getFacultad(),
                Integer.parseInt(estudianteComando.getAnnoIngreso()),
                estudianteComando.getSemestreActual(),
                Integer.parseInt(estudianteComando.getCreditosAprobados()),
                Float.parseFloat(estudianteComando.getPromedioGeneral()),
                estudianteComando.getEstadoAcademico(),
                estudianteComando.getModalidad(),
                Integer.parseInt(estudianteComando.getTiempoLlegadaUniversidad()),
                estudianteComando.getMedioDeTransporte()
                );
    }
}