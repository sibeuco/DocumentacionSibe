package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.modelo.CiudadResidencia;
import co.edu.uco.sibe.dominio.modelo.Estudiante;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstudianteMapeadorTest {

    @Mock
    private InternoCiudadResidenciaMapeador internoCiudadResidenciaMapeador;

    private EstudianteMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new EstudianteMapeador(internoCiudadResidenciaMapeador);
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        CiudadResidenciaEntidad ciudadEntidad = new CiudadResidenciaEntidad(UUID.randomUUID(), "Medellín");
        InternoCiudadResidenciaEntidad internoCiudadEntidad = new InternoCiudadResidenciaEntidad(UUID.randomUUID(), ciudadEntidad);

        EstudianteEntidad entidad = new EstudianteEntidad(
                id, "Ana García", "321654", internoCiudadEntidad,
                "CARNET-100", "F", LocalDate.of(2000, 1, 1), "Colombiana", "Soltero/a",
                "ana@personal.com", "ana@uco.edu.co", "Ingeniería de Sistemas",
                "Facultad de Ingeniería", 2020, "8", 120,
                4.0f, "Activo", "Presencial", 30, "Bus"
        );

        CiudadResidencia ciudadModelo = CiudadResidencia.construir(UUID.randomUUID(), "Medellín");
        when(internoCiudadResidenciaMapeador.construirModelo(internoCiudadEntidad)).thenReturn(ciudadModelo);

        Estudiante modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
        assertEquals("Ana García", modelo.getNombreCompleto());
        assertEquals("321654", modelo.getNumeroIdentificacion());
        assertEquals("Ingeniería de Sistemas", modelo.getProgramaAcademico());
    }

    @Test
    void deberiaConstruirEntidadDesdeModelo() {
        UUID id = UUID.randomUUID();
        CiudadResidencia ciudad = CiudadResidencia.construir(UUID.randomUUID(), "Bogotá");
        InternoCiudadResidenciaEntidad internoCiudadEntidad = mock(InternoCiudadResidenciaEntidad.class);

        Estudiante estudiante = Estudiante.construir(
                id, "Carlos Pérez", "654321", ciudad,
                "CARNET-200", "M", LocalDate.of(1999, 3, 15), "Colombiana", "Soltero/a",
                "carlos@personal.com", "carlos@uco.edu.co", "Derecho",
                "Facultad de Derecho", 2019, "10", 150,
                3.8f, "Activo", "Presencial", 45, "Carro"
        );

        when(internoCiudadResidenciaMapeador.construirEntidad(ciudad)).thenReturn(internoCiudadEntidad);

        EstudianteEntidad entidad = mapeador.construirEntidad(estudiante);

        assertEquals(id, entidad.getIdentificador());
        assertEquals("Carlos Pérez", entidad.getNombreCompleto());
    }

    @Test
    void deberiaModificarEntidad() {
        UUID id = UUID.randomUUID();
        CiudadResidenciaEntidad ciudadEntidad = new CiudadResidenciaEntidad(UUID.randomUUID(), "Medellín");
        InternoCiudadResidenciaEntidad internoCiudadEntidad = new InternoCiudadResidenciaEntidad(UUID.randomUUID(), ciudadEntidad);

        EstudianteEntidad entidad = new EstudianteEntidad(
                id, "Original", "000", internoCiudadEntidad,
                "C-OLD", "M", LocalDate.of(1990, 1, 1), "Colombiana", "Soltero/a",
                "old@personal.com", "old@uco.edu.co", "Prog viejo",
                "Fac vieja", 2015, "1", 0,
                0.0f, "Inactivo", "Virtual", 10, "A pie"
        );

        CiudadResidencia nuevaCiudad = CiudadResidencia.construir(UUID.randomUUID(), "Cali");
        Estudiante estudiante = Estudiante.construir(
                UUID.randomUUID(), "Nuevo", "999", nuevaCiudad,
                "C-NEW", "F", LocalDate.of(2001, 2, 2), "Venezolana", "Casado/a",
                "new@personal.com", "new@uco.edu.co", "Prog nuevo",
                "Fac nueva", 2023, "2", 30,
                4.5f, "Activo", "Presencial", 20, "Metro"
        );

        mapeador.modificarEntidad(entidad, estudiante);

        assertEquals("Nuevo", entidad.getNombreCompleto());
        assertEquals("999", entidad.getNumeroIdentificacion());
        assertEquals("C-NEW", entidad.getIdCarnet());
        assertEquals("F", entidad.getSexo());
        assertEquals("Prog nuevo", entidad.getProgramaAcademico());
    }
}
