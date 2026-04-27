package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.ParticipanteDTO;
import co.edu.uco.sibe.dominio.enums.TipoInterno;
import co.edu.uco.sibe.dominio.enums.TipoParticipante;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.*;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTipoPrograma;

@Component
@AllArgsConstructor
public class ParticipanteDetalladoMapeador {
    private final RelacionLaboralMapeador relacionLaboralMapeador;
    private final CentroCostosMapeador centroCostosMapeador;

    public List<ParticipanteDTO> construirDTOs(List<ParticipanteEntidad> participantes) {
        return participantes
                .stream()
                .map(this::construirDTO)
                .toList();
    }

    public Page<ParticipanteDTO> construirDTOsPaginado(Page<ParticipanteEntidad> entidades) {
        var dtos = entidades.getContent().stream()
                .map(this::construirDTO)
                .toList();
        return new PageImpl<>(dtos, entidades.getPageable(), entidades.getTotalElements());
    }

    public ParticipanteDTO construirDTO(ParticipanteEntidad entidadProxy) {
        if (esNulo(entidadProxy)) {
            return null;
        }

        var entidad = (ParticipanteEntidad) Hibernate.unproxy(entidadProxy);
        var miembroProxy = entidad.getMiembro();

        if (esNulo(miembroProxy)) {
            return null;
        }

        var miembro = (MiembroEntidad) Hibernate.unproxy(miembroProxy);
        var dto = inicializarDtoConDefaults();

        dto.setIdentificador(entidad.getIdentificador().toString());
        dto.setNombreCompleto(miembro.getNombreCompleto());
        dto.setNumeroIdentificacion(miembro.getNumeroIdentificacion());

        if (entidad instanceof ParticipanteEstudianteEntidad e) {
            var estudiante = (EstudianteEntidad) miembro;

            dto.setTipo(TipoInterno.ESTUDIANTE.name());
            dto.setFechaNacimiento(estudiante.getFechaNacimiento().toString());
            dto.setNacionalidad(estudiante.getNacionalidad());
            dto.setEstadoCivil(e.getEstadoCivil());
            dto.setCorreoPersonal(estudiante.getCorreoPersonal());
            dto.setCorreoInstitucional(estudiante.getCorreoInstitucional());
            dto.setProgramaAcademico(e.getProgramaAcademico());
            dto.setTipoProgramaAcademico(obtenerTipoPrograma(e.getProgramaAcademico()));
            dto.setFacultad(e.getFacultad());
            dto.setAnnoIngreso(e.getAnnoIngreso());
            dto.setSemestreActual(e.getSemestreActual());
            dto.setCreditosAprobados(e.getCreditosAprobados());
            dto.setPromedioGeneral(e.getPromedioGeneral());
            dto.setEstadoAcademico(e.getEstadoAcademico());
            dto.setModalidadEstudio(e.getModalidadEstudio());
            dto.setTiempoLlegadaUniversidad(e.getTiempoLlegadaUniversidad());
            dto.setMedioTransporte(e.getMedioTransporte());
        } else if (entidad instanceof ParticipanteEmpleadoEntidad e) {
            dto.setRelacionLaboral(relacionLaboralMapeador.construirDTO(e.getRelacionLaboral().getRelacionLaboral()));
            dto.setCentroCostos(centroCostosMapeador.construirDTO(e.getCentroCostos().getCentroCostos()));
            dto.setTipo(TipoInterno.EMPLEADO.name());
        }

        return dto;
    }

    private ParticipanteDTO inicializarDtoConDefaults() {
        var dto = new ParticipanteDTO();

        dto.setTipo(TipoParticipante.EXTERNO.name());
        dto.setFechaNacimiento(VACIO);
        dto.setNacionalidad(VACIO);
        dto.setEstadoCivil(VACIO);
        dto.setCorreoPersonal(VACIO);
        dto.setCorreoInstitucional(VACIO);
        dto.setProgramaAcademico(VACIO);
        dto.setTipoProgramaAcademico(VACIO);
        dto.setFacultad(VACIO);
        dto.setSemestreActual(VACIO);
        dto.setEstadoAcademico(VACIO);
        dto.setModalidadEstudio(VACIO);
        dto.setMedioTransporte(VACIO);

        return dto;
    }
}