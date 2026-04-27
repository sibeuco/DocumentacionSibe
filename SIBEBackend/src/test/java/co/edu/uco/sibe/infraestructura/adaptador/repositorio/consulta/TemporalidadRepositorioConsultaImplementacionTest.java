package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.TemporalidadDTO;
import co.edu.uco.sibe.dominio.modelo.Temporalidad;
import co.edu.uco.sibe.infraestructura.adaptador.dao.TemporalidadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.TemporalidadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.TemporalidadMapeador;
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
class TemporalidadRepositorioConsultaImplementacionTest {

    @Mock private TemporalidadDAO temporalidadDAO;
    @Mock private TemporalidadMapeador temporalidadMapeador;

    private TemporalidadRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new TemporalidadRepositorioConsultaImplementacion(temporalidadDAO, temporalidadMapeador);
    }

    @Test
    void deberiaConsultarDTOs() {
        List<TemporalidadEntidad> entidades = List.of(new TemporalidadEntidad());
        when(temporalidadDAO.findAll()).thenReturn(entidades);
        when(temporalidadMapeador.construirDTOs(entidades)).thenReturn(List.of(new TemporalidadDTO()));

        List<TemporalidadDTO> resultado = repositorio.consultarDTOs();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarPorIdentificadorExistente() {
        UUID id = UUID.randomUUID();
        TemporalidadEntidad entidad = new TemporalidadEntidad();
        Temporalidad modelo = mock(Temporalidad.class);
        when(temporalidadDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(temporalidadMapeador.construirModelo(entidad)).thenReturn(modelo);

        Temporalidad resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoIdentificadorNoExiste() {
        UUID id = UUID.randomUUID();
        when(temporalidadDAO.findById(id)).thenReturn(Optional.empty());

        Temporalidad resultado = repositorio.consultarPorIdentificador(id);

        assertNull(resultado);
    }

    @Test
    void deberiaVerificarHayDatos() {
        when(temporalidadDAO.count()).thenReturn(3L);

        assertTrue(repositorio.hayDatos());
    }

    @Test
    void deberiaVerificarNoHayDatos() {
        when(temporalidadDAO.count()).thenReturn(0L);

        assertFalse(repositorio.hayDatos());
    }

    @Test
    void deberiaConsultarPorNombreExistente() {
        TemporalidadEntidad entidad = new TemporalidadEntidad();
        Temporalidad modelo = mock(Temporalidad.class);
        when(temporalidadDAO.findByNombre("Mensual")).thenReturn(entidad);
        when(temporalidadMapeador.construirModelo(entidad)).thenReturn(modelo);

        Temporalidad resultado = repositorio.consultarPorNombre("Mensual");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoNombreNoExiste() {
        when(temporalidadDAO.findByNombre("NoExiste")).thenReturn(null);

        Temporalidad resultado = repositorio.consultarPorNombre("NoExiste");

        assertNull(resultado);
    }
}
