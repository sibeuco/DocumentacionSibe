package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.modelo.Estudiante;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EstudianteDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EstudianteEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.EstudianteMapeador;
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
class EstudianteRepositorioConsultaImplementacionTest {

    @Mock private EstudianteDAO estudianteDAO;
    @Mock private EstudianteMapeador estudianteMapeador;

    private EstudianteRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new EstudianteRepositorioConsultaImplementacion(estudianteDAO, estudianteMapeador);
    }

    @Test
    void deberiaRetornarEstudianteCuandoExisteIdentificador() {
        UUID id = UUID.randomUUID();
        EstudianteEntidad entidad = new EstudianteEntidad();
        Estudiante modelo = mock(Estudiante.class);

        when(estudianteDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(estudianteMapeador.construirModelo(entidad)).thenReturn(modelo);

        Estudiante resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExisteIdentificador() {
        UUID id = UUID.randomUUID();

        when(estudianteDAO.findById(id)).thenReturn(Optional.empty());

        assertNull(repositorio.consultarPorIdentificador(id));
    }

    @Test
    void deberiaRetornarEstudianteCuandoExisteIdentificacion() {
        String identificacion = "12345";
        EstudianteEntidad entidad = new EstudianteEntidad();
        Estudiante modelo = mock(Estudiante.class);

        when(estudianteDAO.findFirstByNumeroIdentificacion(identificacion)).thenReturn(entidad);
        when(estudianteMapeador.construirModelo(entidad)).thenReturn(modelo);

        Estudiante resultado = repositorio.consultarPorIdentificacion(identificacion);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExisteIdentificacion() {
        when(estudianteDAO.findFirstByNumeroIdentificacion("99999")).thenReturn(null);

        assertNull(repositorio.consultarPorIdentificacion("99999"));
    }
}
