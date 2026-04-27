package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Estudiante;
import co.edu.uco.sibe.infraestructura.adaptador.dao.CiudadResidenciaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EstudianteDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.CiudadResidenciaEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EstudianteEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.InternoCiudadResidenciaEntidad;
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
class EstudianteRepositorioComandoImplementacionTest {

    @Mock private EstudianteDAO estudianteDAO;
    @Mock private EstudianteMapeador estudianteMapeador;
    @Mock private CiudadResidenciaDAO ciudadResidenciaDAO;

    private EstudianteRepositorioComandoImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new EstudianteRepositorioComandoImplementacion(estudianteDAO, estudianteMapeador, ciudadResidenciaDAO);
    }

    @Test
    void deberiaGuardarEstudianteConCiudadExistente() {
        UUID idEsperado = UUID.randomUUID();
        UUID idCiudad = UUID.randomUUID();
        Estudiante estudiante = mock(Estudiante.class);
        EstudianteEntidad entidad = mock(EstudianteEntidad.class);
        InternoCiudadResidenciaEntidad internoCiudad = mock(InternoCiudadResidenciaEntidad.class);
        CiudadResidenciaEntidad ciudadEntidad = mock(CiudadResidenciaEntidad.class);

        when(estudianteMapeador.construirEntidad(estudiante)).thenReturn(entidad);
        when(entidad.getCiudadResidencia()).thenReturn(internoCiudad);
        when(internoCiudad.getCiudadResidencia()).thenReturn(ciudadEntidad);
        when(ciudadEntidad.getIdentificador()).thenReturn(idCiudad);
        when(ciudadResidenciaDAO.findById(idCiudad)).thenReturn(Optional.of(ciudadEntidad));
        when(estudianteDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(idEsperado);

        UUID resultado = repositorio.guardar(estudiante);

        assertEquals(idEsperado, resultado);
        verify(ciudadResidenciaDAO, never()).save(any());
    }

    @Test
    void deberiaGuardarEstudianteConCiudadNuevaYCrearla() {
        UUID idEsperado = UUID.randomUUID();
        UUID idCiudad = UUID.randomUUID();
        Estudiante estudiante = mock(Estudiante.class);
        EstudianteEntidad entidad = mock(EstudianteEntidad.class);
        InternoCiudadResidenciaEntidad internoCiudad = mock(InternoCiudadResidenciaEntidad.class);
        CiudadResidenciaEntidad ciudadEntidad = mock(CiudadResidenciaEntidad.class);

        when(estudianteMapeador.construirEntidad(estudiante)).thenReturn(entidad);
        when(entidad.getCiudadResidencia()).thenReturn(internoCiudad);
        when(internoCiudad.getCiudadResidencia()).thenReturn(ciudadEntidad);
        when(ciudadEntidad.getIdentificador()).thenReturn(idCiudad);
        when(ciudadResidenciaDAO.findById(idCiudad)).thenReturn(Optional.empty());
        when(estudianteDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(idEsperado);

        UUID resultado = repositorio.guardar(estudiante);

        assertEquals(idEsperado, resultado);
        verify(ciudadResidenciaDAO).save(ciudadEntidad);
    }

    @Test
    void deberiaModificarEstudiante() {
        UUID id = UUID.randomUUID();
        UUID idEsperado = UUID.randomUUID();
        Estudiante estudiante = mock(Estudiante.class);
        EstudianteEntidad entidad = mock(EstudianteEntidad.class);

        when(estudianteDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(estudianteDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(idEsperado);

        UUID resultado = repositorio.modificar(estudiante, id);

        assertEquals(idEsperado, resultado);
        verify(estudianteMapeador).modificarEntidad(entidad, estudiante);
    }
}
