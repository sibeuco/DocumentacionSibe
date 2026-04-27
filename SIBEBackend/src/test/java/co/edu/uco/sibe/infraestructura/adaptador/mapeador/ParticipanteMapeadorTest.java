package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.modelo.*;
import co.edu.uco.sibe.infraestructura.adaptador.dao.InternoDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.MiembroDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipanteMapeadorTest {

    @Mock
    private MiembroMapeador miembroMapeador;

    @Mock
    private InternoCiudadResidenciaMapeador internoCiudadResidenciaMapeador;

    @Mock
    private EmpleadoRelacionLaboralMapeador empleadoRelacionLaboralMapeador;

    @Mock
    private EmpleadoCentroCostosMapeador empleadoCentroCostosMapeador;

    @Mock
    private MiembroDAO miembroDAO;

    @Mock
    private InternoDAO internoDAO;

    private ParticipanteMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new ParticipanteMapeador(miembroMapeador, internoCiudadResidenciaMapeador, empleadoRelacionLaboralMapeador, empleadoCentroCostosMapeador, miembroDAO, internoDAO);
    }

    @Test
    void deberiaConstruirEntidadExternoNuevo() {
        UUID participanteId = UUID.randomUUID();
        UUID miembroId = UUID.randomUUID();
        Externo externo = Externo.construir(miembroId, "Externo Test", "EXT-001");
        ParticipanteExterno dominio = ParticipanteExterno.construir(participanteId, externo);

        when(miembroDAO.findById(miembroId)).thenReturn(Optional.empty());
        ExternoEntidad externoEntidad = new ExternoEntidad();
        externoEntidad.setIdentificador(miembroId);
        externoEntidad.setNombreCompleto("Externo Test");
        externoEntidad.setNumeroIdentificacion("EXT-001");
        when(miembroDAO.save(any(MiembroEntidad.class))).thenReturn(externoEntidad);

        ParticipanteEntidad entidad = mapeador.construirEntidad(dominio);

        assertNotNull(entidad);
        assertTrue(entidad instanceof ParticipanteExternoEntidad);
    }

    @Test
    void deberiaRetornarNullCuandoDominioEsNull() {
        ParticipanteEntidad resultado = mapeador.construirEntidad(null);

        assertNull(resultado);
    }

    @Test
    void deberiaConstruirModeloDesdeEntidadNull() {
        Participante resultado = mapeador.construirModelo(null);

        assertNull(resultado);
    }

    @Test
    void deberiaConstruirModeloDesdeParticipanteExternoEntidad() {
        UUID participanteId = UUID.randomUUID();
        UUID miembroId = UUID.randomUUID();
        ExternoEntidad externoEntidad = new ExternoEntidad();
        externoEntidad.setIdentificador(miembroId);
        externoEntidad.setNombreCompleto("Externo");
        externoEntidad.setNumeroIdentificacion("EXT-001");
        ParticipanteExternoEntidad entidad = new ParticipanteExternoEntidad(participanteId, externoEntidad);

        Externo externo = Externo.construir(miembroId, "Externo", "EXT-001");
        when(miembroMapeador.construirModelo(externoEntidad)).thenReturn(externo);

        Participante modelo = mapeador.construirModelo(entidad);

        assertNotNull(modelo);
        assertTrue(modelo instanceof ParticipanteExterno);
    }

    @Test
    void deberiaConstruirModeloDesdeParticipanteEntidadGenerica() {
        UUID participanteId = UUID.randomUUID();
        UUID miembroId = UUID.randomUUID();
        MiembroEntidad miembroEntidad = new MiembroEntidad(miembroId, "Genérico", "GEN-001");
        ParticipanteEntidad entidad = new ParticipanteEntidad(participanteId, miembroEntidad);

        Miembro miembro = Miembro.construir(miembroId, "Genérico", "GEN-001");
        when(miembroMapeador.construirModelo(miembroEntidad)).thenReturn(miembro);

        Participante modelo = mapeador.construirModelo(entidad);

        assertNotNull(modelo);
        assertEquals(participanteId, modelo.getIdentificador());
    }

    @Test
    void deberiaConstruirEntidadExternoExistente() {
        UUID participanteId = UUID.randomUUID();
        UUID miembroId = UUID.randomUUID();
        Externo externo = Externo.construir(miembroId, "Externo Existente", "EXT-002");
        ParticipanteExterno dominio = ParticipanteExterno.construir(participanteId, externo);

        ExternoEntidad externoEntidad = new ExternoEntidad();
        externoEntidad.setIdentificador(miembroId);
        externoEntidad.setNombreCompleto("Externo Existente");
        externoEntidad.setNumeroIdentificacion("EXT-002");
        when(miembroDAO.findById(miembroId)).thenReturn(Optional.of(externoEntidad));

        ParticipanteEntidad entidad = mapeador.construirEntidad(dominio);

        assertNotNull(entidad);
        assertTrue(entidad instanceof ParticipanteExternoEntidad);
        verify(miembroDAO, never()).save(any());
    }

    @Test
    void deberiaConstruirEntidadEstudiante() {
        UUID participanteId = UUID.randomUUID();
        UUID miembroId = UUID.randomUUID();
        Miembro miembro = Miembro.construir(miembroId, "Estudiante Test", "EST-001");
        CiudadResidencia ciudad = CiudadResidencia.construir();
        ParticipanteEstudiante dominio = ParticipanteEstudiante.construir(
                participanteId, miembro, ciudad, "CARNET-001", "M",
                "Soltero", "Ingenieria", "Ingenieria", 2020, "8", 120, 4.0f,
                "Activo", "Presencial", 30, "Bus"
        );

        InternoCiudadResidenciaEntidad ciudadEntidad = mock(InternoCiudadResidenciaEntidad.class);
        EstudianteEntidad estudianteEntidad = mock(EstudianteEntidad.class);
        when(estudianteEntidad.getCiudadResidencia()).thenReturn(ciudadEntidad);
        when(internoDAO.findById(miembroId)).thenReturn(Optional.of(estudianteEntidad));

        ParticipanteEntidad entidad = mapeador.construirEntidad(dominio);

        assertNotNull(entidad);
        assertTrue(entidad instanceof ParticipanteEstudianteEntidad);
    }

    @Test
    void deberiaConstruirEntidadEmpleado() {
        UUID participanteId = UUID.randomUUID();
        UUID miembroId = UUID.randomUUID();
        Miembro miembro = Miembro.construir(miembroId, "Empleado Test", "EMP-001");
        CiudadResidencia ciudad = CiudadResidencia.construir();
        RelacionLaboral relacionLaboral = RelacionLaboral.construir();
        CentroCostos centroCostos = CentroCostos.construir();
        ParticipanteEmpleado dominio = ParticipanteEmpleado.construir(
                participanteId, miembro, ciudad, "CARNET-002", "F",
                relacionLaboral, centroCostos
        );

        InternoCiudadResidenciaEntidad ciudadEntidad = mock(InternoCiudadResidenciaEntidad.class);
        EmpleadoRelacionLaboralEntidad relLabEntidad = mock(EmpleadoRelacionLaboralEntidad.class);
        EmpleadoCentroCostosEntidad ccEntidad = mock(EmpleadoCentroCostosEntidad.class);
        EmpleadoEntidad empleadoEntidad = mock(EmpleadoEntidad.class);
        when(empleadoEntidad.getCiudadResidencia()).thenReturn(ciudadEntidad);
        when(empleadoEntidad.getRelacionLaboral()).thenReturn(relLabEntidad);
        when(empleadoEntidad.getCentroCostos()).thenReturn(ccEntidad);
        when(internoDAO.findById(miembroId)).thenReturn(Optional.of(empleadoEntidad));

        ParticipanteEntidad entidad = mapeador.construirEntidad(dominio);

        assertNotNull(entidad);
        assertTrue(entidad instanceof ParticipanteEmpleadoEntidad);
    }

    @Test
    void deberiaConstruirEntidadGenericaCuandoNoEsSubtipoConocido() {
        UUID participanteId = UUID.randomUUID();
        UUID miembroId = UUID.randomUUID();
        Miembro miembro = Miembro.construir(miembroId, "Generico Test", "GEN-001");
        Participante dominio = Participante.construir(participanteId, miembro);

        InternoEntidad internoEntidad = mock(InternoEntidad.class);
        when(internoDAO.findById(miembroId)).thenReturn(Optional.of(internoEntidad));

        ParticipanteEntidad entidad = mapeador.construirEntidad(dominio);

        assertNotNull(entidad);
        assertEquals(participanteId, entidad.getIdentificador());
    }

    @Test
    void deberiaConstruirEntidadLanzarExcepcionCuandoInternoNoExiste() {
        UUID participanteId = UUID.randomUUID();
        UUID miembroId = UUID.randomUUID();
        Miembro miembro = Miembro.construir(miembroId, "No Existe", "NE-001");
        Participante dominio = Participante.construir(participanteId, miembro);

        when(internoDAO.findById(miembroId)).thenReturn(Optional.empty());

        assertThrows(jakarta.persistence.EntityNotFoundException.class, () -> mapeador.construirEntidad(dominio));
    }

    @Test
    void deberiaConstruirModeloDesdeParticipanteEstudianteEntidad() {
        UUID participanteId = UUID.randomUUID();
        UUID miembroId = UUID.randomUUID();
        EstudianteEntidad estudianteEntidad = new EstudianteEntidad();
        estudianteEntidad.setIdentificador(miembroId);
        estudianteEntidad.setNombreCompleto("Estudiante");
        estudianteEntidad.setNumeroIdentificacion("EST-001");

        InternoCiudadResidenciaEntidad ciudadEntidad = mock(InternoCiudadResidenciaEntidad.class);
        ParticipanteEstudianteEntidad entidad = new ParticipanteEstudianteEntidad(
                participanteId, estudianteEntidad, ciudadEntidad, "CARNET-001", "M",
                "Soltero", "Ingenieria", "Ingenieria", 2020, "8", 120, 4.0f,
                "Activo", "Presencial", 30, "Bus"
        );

        Miembro miembro = Miembro.construir(miembroId, "Estudiante", "EST-001");
        CiudadResidencia ciudadModelo = CiudadResidencia.construir();
        when(miembroMapeador.construirModelo(estudianteEntidad)).thenReturn(miembro);
        when(internoCiudadResidenciaMapeador.construirModelo(ciudadEntidad)).thenReturn(ciudadModelo);

        Participante modelo = mapeador.construirModelo(entidad);

        assertNotNull(modelo);
        assertTrue(modelo instanceof ParticipanteEstudiante);
    }

    @Test
    void deberiaConstruirModeloDesdeParticipanteEmpleadoEntidad() {
        UUID participanteId = UUID.randomUUID();
        UUID miembroId = UUID.randomUUID();
        EmpleadoEntidad empleadoEntidad = new EmpleadoEntidad();
        empleadoEntidad.setIdentificador(miembroId);
        empleadoEntidad.setNombreCompleto("Empleado");
        empleadoEntidad.setNumeroIdentificacion("EMP-001");

        InternoCiudadResidenciaEntidad ciudadEntidad = mock(InternoCiudadResidenciaEntidad.class);
        EmpleadoRelacionLaboralEntidad relLabEntidad = mock(EmpleadoRelacionLaboralEntidad.class);
        EmpleadoCentroCostosEntidad ccEntidad = mock(EmpleadoCentroCostosEntidad.class);
        ParticipanteEmpleadoEntidad entidad = new ParticipanteEmpleadoEntidad(
                participanteId, empleadoEntidad, ciudadEntidad, "CARNET-002", "F",
                relLabEntidad, ccEntidad
        );

        Miembro miembro = Miembro.construir(miembroId, "Empleado", "EMP-001");
        CiudadResidencia ciudadModelo = CiudadResidencia.construir();
        RelacionLaboral relLabModelo = RelacionLaboral.construir();
        CentroCostos ccModelo = CentroCostos.construir();
        when(miembroMapeador.construirModelo(empleadoEntidad)).thenReturn(miembro);
        when(internoCiudadResidenciaMapeador.construirModelo(ciudadEntidad)).thenReturn(ciudadModelo);
        when(empleadoRelacionLaboralMapeador.construirModelo(relLabEntidad)).thenReturn(relLabModelo);
        when(empleadoCentroCostosMapeador.construirModelo(ccEntidad)).thenReturn(ccModelo);

        Participante modelo = mapeador.construirModelo(entidad);

        assertNotNull(modelo);
        assertTrue(modelo instanceof ParticipanteEmpleado);
    }
}
