package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.DatosEstudianteComando;
import co.edu.uco.sibe.dominio.modelo.CiudadResidencia;
import co.edu.uco.sibe.dominio.modelo.Estudiante;
import co.edu.uco.sibe.dominio.puerto.consulta.EstudianteRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstudianteFabricaTest {

    @Mock
    private CiudadResidenciaFabrica ciudadResidenciaFabrica;
    @Mock
    private EstudianteRepositorioConsulta estudianteRepositorioConsulta;

    private EstudianteFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new EstudianteFabrica(ciudadResidenciaFabrica, estudianteRepositorioConsulta);
    }

    @Test
    void deberiaConstruirEstudianteDesdeComando() {
        CiudadResidencia ciudad = CiudadResidencia.construir(UUID.randomUUID(), "Medellin");

        when(estudianteRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);
        when(ciudadResidenciaFabrica.construir("Medellin")).thenReturn(ciudad);

        DatosEstudianteComando comando = new DatosEstudianteComando(
                "Juan Perez",
                "1234567890",
                "CARN001",
                "1/15/2000",
                "M",
                "Colombiana",
                "Soltero",
                "Calle 50 #30-20",
                "Medellin",
                "3001234567",
                "3001234567",
                "correo@personal.com",
                "correo@institucional.edu.co",
                "Ingenieria de Sistemas",
                "Facultad de Ingenieria",
                "2022",
                "6",
                "90",
                "4.2",
                "Activo",
                "Presencial",
                "30",
                "Bus"
        );

        Estudiante resultado = fabrica.construir(comando);

        assertNotNull(resultado.getIdentificador());
        assertEquals("Juan Perez", resultado.getNombreCompleto());
        assertEquals("1234567890", resultado.getNumeroIdentificacion());
        assertEquals(ciudad, resultado.getCiudadResidencia());
        assertEquals("correo@personal.com", resultado.getCorreoPersonal());
        assertEquals("Ingenieria de Sistemas", resultado.getProgramaAcademico());
    }
}
