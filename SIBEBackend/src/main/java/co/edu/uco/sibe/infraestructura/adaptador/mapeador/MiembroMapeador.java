package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.MiembroDTO;
import co.edu.uco.sibe.dominio.enums.TipoInterno;
import co.edu.uco.sibe.dominio.enums.TipoParticipante;
import co.edu.uco.sibe.dominio.modelo.Empleado;
import co.edu.uco.sibe.dominio.modelo.Estudiante;
import co.edu.uco.sibe.dominio.modelo.Externo;
import co.edu.uco.sibe.dominio.modelo.Miembro;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EmpleadoEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EstudianteEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ExternoEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.MiembroEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.NO_APLICA;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTipoPrograma;

@Component
@AllArgsConstructor
public class MiembroMapeador {
    private final EstudianteMapeador estudianteMapeador;
    private final EmpleadoMapeador empleadoMapeador;

    public Miembro construirModelo(MiembroEntidad entidad) {
        if (entidad instanceof EstudianteEntidad e) {
            return estudianteMapeador.construirModelo(e);
        }

        if (entidad instanceof EmpleadoEntidad e) {
            return empleadoMapeador.construirModelo(e);
        }

        if (entidad instanceof ExternoEntidad e) {
            return Externo.construir(
                    e.getIdentificador(),
                    e.getNombreCompleto(),
                    e.getNumeroIdentificacion()
            );
        }

        if (entidad != null) {
            return Miembro.construir(
                    entidad.getIdentificador(),
                    entidad.getNombreCompleto(),
                    entidad.getNumeroIdentificacion()
            );
        }

        return null;
    }

    public MiembroEntidad construirEntidad(Miembro miembro) {
        if (miembro instanceof Estudiante e) {
            return estudianteMapeador.construirEntidad(e);
        }

        if (miembro instanceof Empleado e) {
            return empleadoMapeador.construirEntidad(e);
        }

        if (miembro instanceof Externo e) {
            var entidad = new ExternoEntidad();
            entidad.setIdentificador(e.getIdentificador());
            entidad.setNombreCompleto(e.getNombreCompleto());
            entidad.setNumeroIdentificacion(e.getNumeroIdentificacion());
            return entidad;
        }

        if (miembro != null) {
            return new MiembroEntidad(
                    miembro.getIdentificador(),
                    miembro.getNombreCompleto(),
                    miembro.getNumeroIdentificacion()
            );
        }

        return null;
    }

    public MiembroDTO construirDTO(MiembroEntidad miembro) {
        if (miembro instanceof EstudianteEntidad e) {
            return new MiembroDTO(
                    e.getIdentificador().toString(),
                    e.getNombreCompleto(),
                    e.getNumeroIdentificacion(),
                    e.getProgramaAcademico(),
                    obtenerTipoPrograma(e.getProgramaAcademico()),
                    e.getCorreoInstitucional(),
                    TipoParticipante.INTERNO.name(),
                    TipoInterno.ESTUDIANTE.name()
            );
        }

        if (miembro instanceof EmpleadoEntidad e) {
            return new MiembroDTO(
                    e.getIdentificador().toString(),
                    e.getNombreCompleto(),
                    e.getNumeroIdentificacion(),
                    NO_APLICA,
                    NO_APLICA,
                    NO_APLICA,
                    TipoParticipante.INTERNO.name(),
                    TipoInterno.EMPLEADO.name()
            );
        }

        if (miembro instanceof ExternoEntidad e) {
            return new MiembroDTO(
                    e.getIdentificador().toString(),
                    e.getNombreCompleto(),
                    e.getNumeroIdentificacion(),
                    NO_APLICA,
                    NO_APLICA,
                    NO_APLICA,
                    TipoParticipante.EXTERNO.name(),
                    NO_APLICA
            );
        }

        return null;
    }
}