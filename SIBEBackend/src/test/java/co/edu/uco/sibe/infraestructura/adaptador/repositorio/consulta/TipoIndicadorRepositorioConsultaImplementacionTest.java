package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.TipoIndicadorDTO;
import co.edu.uco.sibe.dominio.modelo.TipoIndicador;
import co.edu.uco.sibe.infraestructura.adaptador.dao.TipoIndicadorDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.TipoIndicadorEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.TipoIndicadorMapeador;
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
class TipoIndicadorRepositorioConsultaImplementacionTest {

    @Mock private TipoIndicadorDAO tipoIndicadorDAO;
    @Mock private TipoIndicadorMapeador tipoIndicadorMapeador;

    private TipoIndicadorRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new TipoIndicadorRepositorioConsultaImplementacion(tipoIndicadorDAO, tipoIndicadorMapeador);
    }

    @Test
    void deberiaConsultarDTOs() {
        List<TipoIndicadorEntidad> entidades = List.of(new TipoIndicadorEntidad());
        when(tipoIndicadorDAO.findAll()).thenReturn(entidades);
        when(tipoIndicadorMapeador.construirDTOs(entidades)).thenReturn(List.of(new TipoIndicadorDTO()));

        List<TipoIndicadorDTO> resultado = repositorio.consultarDTOs();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarPorIdentificadorExistente() {
        UUID id = UUID.randomUUID();
        TipoIndicadorEntidad entidad = new TipoIndicadorEntidad();
        TipoIndicador modelo = mock(TipoIndicador.class);
        when(tipoIndicadorDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(tipoIndicadorMapeador.construirModelo(entidad)).thenReturn(modelo);

        TipoIndicador resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoIdentificadorNoExiste() {
        UUID id = UUID.randomUUID();
        when(tipoIndicadorDAO.findById(id)).thenReturn(Optional.empty());

        TipoIndicador resultado = repositorio.consultarPorIdentificador(id);

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarPorNaturalezaExistente() {
        TipoIndicadorEntidad entidad = new TipoIndicadorEntidad();
        TipoIndicador modelo = mock(TipoIndicador.class);
        when(tipoIndicadorDAO.findByNaturaleza("Eficiencia")).thenReturn(entidad);
        when(tipoIndicadorMapeador.construirModelo(entidad)).thenReturn(modelo);

        TipoIndicador resultado = repositorio.consultarPorNaturaleza("Eficiencia");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoNaturalezaNoExiste() {
        when(tipoIndicadorDAO.findByNaturaleza("NoExiste")).thenReturn(null);

        TipoIndicador resultado = repositorio.consultarPorNaturaleza("NoExiste");

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarPorTipologiaExistente() {
        TipoIndicadorEntidad entidad = new TipoIndicadorEntidad();
        TipoIndicador modelo = mock(TipoIndicador.class);
        when(tipoIndicadorDAO.findByTipologia("Gestión")).thenReturn(entidad);
        when(tipoIndicadorMapeador.construirModelo(entidad)).thenReturn(modelo);

        TipoIndicador resultado = repositorio.consultarPorTipologia("Gestión");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoTipologiaNoExiste() {
        when(tipoIndicadorDAO.findByTipologia("NoExiste")).thenReturn(null);

        TipoIndicador resultado = repositorio.consultarPorTipologia("NoExiste");

        assertNull(resultado);
    }

    @Test
    void deberiaVerificarHayDatos() {
        when(tipoIndicadorDAO.count()).thenReturn(5L);

        assertTrue(repositorio.hayDatos());
    }

    @Test
    void deberiaVerificarNoHayDatos() {
        when(tipoIndicadorDAO.count()).thenReturn(0L);

        assertFalse(repositorio.hayDatos());
    }
}
