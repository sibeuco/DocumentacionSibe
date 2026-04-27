package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.MiembroDTO;
import co.edu.uco.sibe.dominio.modelo.*;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MiembroMapeadorTest {

    @Mock
    private EstudianteMapeador estudianteMapeador;

    @Mock
    private EmpleadoMapeador empleadoMapeador;

    private MiembroMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new MiembroMapeador(estudianteMapeador, empleadoMapeador);
    }

    @Test
    void deberiaConstruirModeloDesdeEstudianteEntidad() {
        UUID id = UUID.randomUUID();
        EstudianteEntidad entidad = mock(EstudianteEntidad.class);
        Estudiante estudiante = mock(Estudiante.class);

        when(estudianteMapeador.construirModelo(entidad)).thenReturn(estudiante);

        Miembro resultado = mapeador.construirModelo(entidad);

        assertEquals(estudiante, resultado);
    }

    @Test
    void deberiaConstruirModeloDesdeEmpleadoEntidad() {
        UUID id = UUID.randomUUID();
        EmpleadoEntidad entidad = mock(EmpleadoEntidad.class);
        Empleado empleado = mock(Empleado.class);

        when(empleadoMapeador.construirModelo(entidad)).thenReturn(empleado);

        Miembro resultado = mapeador.construirModelo(entidad);

        assertEquals(empleado, resultado);
    }

    @Test
    void deberiaConstruirModeloDesdeExternoEntidad() {
        UUID id = UUID.randomUUID();
        ExternoEntidad entidad = mock(ExternoEntidad.class);
        when(entidad.getIdentificador()).thenReturn(id);
        when(entidad.getNombreCompleto()).thenReturn("Externo Test");
        when(entidad.getNumeroIdentificacion()).thenReturn("EXT-001");

        Miembro resultado = mapeador.construirModelo(entidad);

        assertNotNull(resultado);
        assertTrue(resultado instanceof Externo);
    }

    @Test
    void deberiaConstruirModeloDesdeEntidadGenerica() {
        UUID id = UUID.randomUUID();
        MiembroEntidad entidad = new MiembroEntidad(id, "Miembro Gen", "MG-001");

        Miembro resultado = mapeador.construirModelo(entidad);

        assertEquals(id, resultado.getIdentificador());
        assertEquals("Miembro Gen", resultado.getNombreCompleto());
    }

    @Test
    void deberiaRetornarNullCuandoEntidadEsNull() {
        Miembro resultado = mapeador.construirModelo(null);

        assertNull(resultado);
    }

    @Test
    void deberiaConstruirEntidadDesdeEstudiante() {
        Estudiante estudiante = mock(Estudiante.class);
        EstudianteEntidad entidad = mock(EstudianteEntidad.class);

        when(estudianteMapeador.construirEntidad(estudiante)).thenReturn(entidad);

        MiembroEntidad resultado = mapeador.construirEntidad(estudiante);

        assertEquals(entidad, resultado);
    }

    @Test
    void deberiaConstruirEntidadDesdeEmpleado() {
        Empleado empleado = mock(Empleado.class);
        EmpleadoEntidad entidad = mock(EmpleadoEntidad.class);

        when(empleadoMapeador.construirEntidad(empleado)).thenReturn(entidad);

        MiembroEntidad resultado = mapeador.construirEntidad(empleado);

        assertEquals(entidad, resultado);
    }

    @Test
    void deberiaConstruirEntidadDesdeExterno() {
        UUID id = UUID.randomUUID();
        Externo externo = Externo.construir(id, "Ext Name", "EXT-002");

        MiembroEntidad resultado = mapeador.construirEntidad(externo);

        assertNotNull(resultado);
        assertTrue(resultado instanceof ExternoEntidad);
        assertEquals(id, resultado.getIdentificador());
    }

    @Test
    void deberiaConstruirEntidadDesdeMiembroGenerico() {
        UUID id = UUID.randomUUID();
        Miembro miembro = Miembro.construir(id, "Genérico", "GEN-001");

        MiembroEntidad resultado = mapeador.construirEntidad(miembro);

        assertEquals(id, resultado.getIdentificador());
    }

    @Test
    void deberiaRetornarNullCuandoMiembroEsNull() {
        MiembroEntidad resultado = mapeador.construirEntidad(null);

        assertNull(resultado);
    }

    @Test
    void deberiaConstruirDTODesdeEstudianteEntidad() {
        EstudianteEntidad entidad = mock(EstudianteEntidad.class);
        when(entidad.getIdentificador()).thenReturn(UUID.randomUUID());
        when(entidad.getNombreCompleto()).thenReturn("Estudiante Test");
        when(entidad.getNumeroIdentificacion()).thenReturn("EST-001");
        when(entidad.getProgramaAcademico()).thenReturn("Ingeniería de Sistemas");
        when(entidad.getCorreoInstitucional()).thenReturn("est@uco.edu.co");

        MiembroDTO dto = mapeador.construirDTO(entidad);

        assertNotNull(dto);
        assertEquals("Estudiante Test", dto.getNombreCompleto());
    }

    @Test
    void deberiaConstruirDTODesdeEmpleadoEntidad() {
        EmpleadoEntidad entidad = mock(EmpleadoEntidad.class);
        when(entidad.getIdentificador()).thenReturn(UUID.randomUUID());
        when(entidad.getNombreCompleto()).thenReturn("Empleado Test");
        when(entidad.getNumeroIdentificacion()).thenReturn("EMP-001");

        MiembroDTO dto = mapeador.construirDTO(entidad);

        assertNotNull(dto);
        assertEquals("Empleado Test", dto.getNombreCompleto());
    }

    @Test
    void deberiaConstruirDTODesdeExternoEntidad() {
        ExternoEntidad entidad = mock(ExternoEntidad.class);
        when(entidad.getIdentificador()).thenReturn(UUID.randomUUID());
        when(entidad.getNombreCompleto()).thenReturn("Externo DTO");
        when(entidad.getNumeroIdentificacion()).thenReturn("EXT-003");

        MiembroDTO dto = mapeador.construirDTO(entidad);

        assertNotNull(dto);
        assertEquals("Externo DTO", dto.getNombreCompleto());
    }

    @Test
    void deberiaRetornarNullDTOCuandoMiembroNoEsTipoConocido() {
        MiembroDTO resultado = mapeador.construirDTO(null);

        assertNull(resultado);
    }
}
