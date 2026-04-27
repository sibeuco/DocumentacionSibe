package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.PublicoInteresDTO;
import co.edu.uco.sibe.dominio.modelo.PublicoInteres;
import co.edu.uco.sibe.infraestructura.adaptador.dao.PublicoInteresDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.PublicoInteresEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.PublicoInteresMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublicoInteresRepositorioConsultaImplementacionTest {

    @Mock private PublicoInteresDAO publicoInteresDAO;
    @Mock private PublicoInteresMapeador publicoInteresMapeador;

    private PublicoInteresRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new PublicoInteresRepositorioConsultaImplementacion(publicoInteresDAO, publicoInteresMapeador);
    }

    @Test
    void deberiaConsultarDTOs() {
        List<PublicoInteresEntidad> entidades = List.of(new PublicoInteresEntidad());
        when(publicoInteresDAO.findAll()).thenReturn(entidades);
        when(publicoInteresMapeador.construirDTOs(entidades)).thenReturn(List.of(new PublicoInteresDTO()));

        List<PublicoInteresDTO> resultado = repositorio.consultarDTOs();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarPorIdentificadorExistente() {
        UUID id = UUID.randomUUID();
        PublicoInteresEntidad entidad = new PublicoInteresEntidad();
        PublicoInteres modelo = mock(PublicoInteres.class);
        when(publicoInteresDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(publicoInteresMapeador.construirModelo(entidad)).thenReturn(modelo);

        PublicoInteres resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoIdentificadorNoExiste() {
        UUID id = UUID.randomUUID();
        when(publicoInteresDAO.findById(id)).thenReturn(Optional.empty());

        PublicoInteres resultado = repositorio.consultarPorIdentificador(id);

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarPorNombreExistente() {
        PublicoInteresEntidad entidad = new PublicoInteresEntidad();
        PublicoInteres modelo = mock(PublicoInteres.class);
        when(publicoInteresDAO.findByNombre("Estudiantes")).thenReturn(entidad);
        when(publicoInteresMapeador.construirModelo(entidad)).thenReturn(modelo);

        PublicoInteres resultado = repositorio.consultarPorNombre("Estudiantes");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoNombreNoExiste() {
        when(publicoInteresDAO.findByNombre("NoExiste")).thenReturn(null);

        PublicoInteres resultado = repositorio.consultarPorNombre("NoExiste");

        assertNull(resultado);
    }

    @Test
    void deberiaVerificarHayDatos() {
        when(publicoInteresDAO.count()).thenReturn(3L);

        assertTrue(repositorio.hayDatos());
    }

    @Test
    void deberiaVerificarNoHayDatos() {
        when(publicoInteresDAO.count()).thenReturn(0L);

        assertFalse(repositorio.hayDatos());
    }

    @Test
    void deberiaConsultarTodosPorIdentificadores() {
        List<UUID> ids = List.of(UUID.randomUUID(), UUID.randomUUID());
        List<PublicoInteresEntidad> entidades = List.of(new PublicoInteresEntidad(), new PublicoInteresEntidad());
        List<PublicoInteres> modelos = List.of(mock(PublicoInteres.class), mock(PublicoInteres.class));
        when(publicoInteresDAO.findAllById(ids)).thenReturn(entidades);
        when(publicoInteresMapeador.construirModelos(entidades)).thenReturn(modelos);

        List<PublicoInteres> resultado = repositorio.consultarTodosPorIdentificadores(ids);

        assertEquals(2, resultado.size());
    }
}
