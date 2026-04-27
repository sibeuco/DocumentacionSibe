package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.ParticipanteDTO;
import co.edu.uco.sibe.dominio.dto.CentroCostosDTO;
import co.edu.uco.sibe.dominio.dto.RelacionLaboralDTO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipanteDetalladoMapeadorTest {

    @Mock
    private RelacionLaboralMapeador relacionLaboralMapeador;

    @Mock
    private CentroCostosMapeador centroCostosMapeador;

    private ParticipanteDetalladoMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new ParticipanteDetalladoMapeador(relacionLaboralMapeador, centroCostosMapeador);
    }

    @Test
    void deberiaRetornarNullCuandoEntidadEsNull() {
        ParticipanteDTO resultado = mapeador.construirDTO(null);

        assertNull(resultado);
    }

    @Test
    void deberiaConstruirListaDTOs() {
        List<ParticipanteEntidad> participantes = List.of();

        List<ParticipanteDTO> dtos = mapeador.construirDTOs(participantes);

        assertTrue(dtos.isEmpty());
    }

    @Test
    void deberiaConstruirDTOParaParticipanteExternoBasico() {
        UUID participanteId = UUID.randomUUID();
        UUID miembroId = UUID.randomUUID();
        ExternoEntidad externoEntidad = new ExternoEntidad();
        externoEntidad.setIdentificador(miembroId);
        externoEntidad.setNombreCompleto("Externo Test");
        externoEntidad.setNumeroIdentificacion("EXT-001");
        ParticipanteExternoEntidad entidad = new ParticipanteExternoEntidad(participanteId, externoEntidad);

        ParticipanteDTO dto = mapeador.construirDTO(entidad);

        assertNotNull(dto);
        assertEquals(participanteId.toString(), dto.getIdentificador());
        assertEquals("Externo Test", dto.getNombreCompleto());
        assertEquals("EXT-001", dto.getNumeroIdentificacion());
        assertEquals("EXTERNO", dto.getTipo());
    }

    @Test
    void deberiaRetornarNullCuandoMiembroEsNull() {
        UUID participanteId = UUID.randomUUID();
        ParticipanteEntidad entidad = new ParticipanteEntidad(participanteId, null);

        ParticipanteDTO dto = mapeador.construirDTO(entidad);

        assertNull(dto);
    }

    @Test
    void deberiaConstruirDTOParaParticipanteEstudiante() {
        UUID participanteId = UUID.randomUUID();
        UUID miembroId = UUID.randomUUID();
        EstudianteEntidad estudianteEntidad = new EstudianteEntidad();
        estudianteEntidad.setIdentificador(miembroId);
        estudianteEntidad.setNombreCompleto("Estudiante Test");
        estudianteEntidad.setNumeroIdentificacion("EST-001");
        estudianteEntidad.setFechaNacimiento(LocalDate.of(2000, 1, 15));
        estudianteEntidad.setNacionalidad("Colombiana");
        estudianteEntidad.setCorreoPersonal("est@correo.com");
        estudianteEntidad.setCorreoInstitucional("est@uco.edu.co");

        InternoCiudadResidenciaEntidad ciudadEntidad = mock(InternoCiudadResidenciaEntidad.class);
        ParticipanteEstudianteEntidad entidad = new ParticipanteEstudianteEntidad(
                participanteId, estudianteEntidad, ciudadEntidad, "CARNET-001", "M",
                "Soltero", "INGENIERIA DE SISTEMAS", "Ingenieria", 2020, "8", 120, 4.0f,
                "Activo", "Presencial", 30, "Bus"
        );

        ParticipanteDTO dto = mapeador.construirDTO(entidad);

        assertNotNull(dto);
        assertEquals("ESTUDIANTE", dto.getTipo());
        assertEquals("Estudiante Test", dto.getNombreCompleto());
        assertEquals("2000-01-15", dto.getFechaNacimiento());
        assertEquals("Colombiana", dto.getNacionalidad());
        assertEquals("Soltero", dto.getEstadoCivil());
        assertEquals("est@correo.com", dto.getCorreoPersonal());
        assertEquals("est@uco.edu.co", dto.getCorreoInstitucional());
        assertEquals("INGENIERIA DE SISTEMAS", dto.getProgramaAcademico());
        assertEquals("Ingenieria", dto.getFacultad());
        assertEquals(2020, dto.getAnnoIngreso());
        assertEquals("8", dto.getSemestreActual());
        assertEquals(120, dto.getCreditosAprobados());
        assertEquals(4.0f, dto.getPromedioGeneral());
        assertEquals("Activo", dto.getEstadoAcademico());
        assertEquals("Presencial", dto.getModalidadEstudio());
        assertEquals(30, dto.getTiempoLlegadaUniversidad());
        assertEquals("Bus", dto.getMedioTransporte());
    }

    @Test
    void deberiaConstruirDTOParaParticipanteEmpleado() {
        UUID participanteId = UUID.randomUUID();
        UUID miembroId = UUID.randomUUID();
        EmpleadoEntidad empleadoEntidad = new EmpleadoEntidad();
        empleadoEntidad.setIdentificador(miembroId);
        empleadoEntidad.setNombreCompleto("Empleado Test");
        empleadoEntidad.setNumeroIdentificacion("EMP-001");

        InternoCiudadResidenciaEntidad ciudadEntidad = mock(InternoCiudadResidenciaEntidad.class);
        RelacionLaboralEntidad relLabEntidadInner = new RelacionLaboralEntidad(UUID.randomUUID(), "001", "Planta");
        EmpleadoRelacionLaboralEntidad relLabEntidad = new EmpleadoRelacionLaboralEntidad(UUID.randomUUID(), relLabEntidadInner);
        CentroCostosEntidad ccEntidadInner = new CentroCostosEntidad(UUID.randomUUID(), "CC01", "Centro Costos 1");
        EmpleadoCentroCostosEntidad ccEntidad = new EmpleadoCentroCostosEntidad(UUID.randomUUID(), ccEntidadInner);

        ParticipanteEmpleadoEntidad entidad = new ParticipanteEmpleadoEntidad(
                participanteId, empleadoEntidad, ciudadEntidad, "CARNET-002", "F",
                relLabEntidad, ccEntidad
        );

        RelacionLaboralDTO relLabDTO = new RelacionLaboralDTO();
        CentroCostosDTO ccDTO = new CentroCostosDTO();
        when(relacionLaboralMapeador.construirDTO(relLabEntidadInner)).thenReturn(relLabDTO);
        when(centroCostosMapeador.construirDTO(ccEntidadInner)).thenReturn(ccDTO);

        ParticipanteDTO dto = mapeador.construirDTO(entidad);

        assertNotNull(dto);
        assertEquals("EMPLEADO", dto.getTipo());
        assertEquals("Empleado Test", dto.getNombreCompleto());
        assertEquals(relLabDTO, dto.getRelacionLaboral());
        assertEquals(ccDTO, dto.getCentroCostos());
    }

    @Test
    void deberiaConstruirListaDTOsConMultiplesElementos() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID miembroId1 = UUID.randomUUID();
        UUID miembroId2 = UUID.randomUUID();

        ExternoEntidad ext1 = new ExternoEntidad();
        ext1.setIdentificador(miembroId1);
        ext1.setNombreCompleto("Ext1");
        ext1.setNumeroIdentificacion("E001");
        ParticipanteExternoEntidad entidad1 = new ParticipanteExternoEntidad(id1, ext1);

        ExternoEntidad ext2 = new ExternoEntidad();
        ext2.setIdentificador(miembroId2);
        ext2.setNombreCompleto("Ext2");
        ext2.setNumeroIdentificacion("E002");
        ParticipanteExternoEntidad entidad2 = new ParticipanteExternoEntidad(id2, ext2);

        List<ParticipanteDTO> dtos = mapeador.construirDTOs(List.of(entidad1, entidad2));

        assertEquals(2, dtos.size());
    }
}
