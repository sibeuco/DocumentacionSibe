package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.TipoIdentificacionDTO;
import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.infraestructura.adaptador.dao.TipoIdentificacionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.TipoIdentificacionEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.TipoIdentificacionMapeador;
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
class TipoIdentificacionRepositorioConsultaImplementacionTest {

    @Mock private TipoIdentificacionDAO tipoIdentificacionDAO;
    @Mock private TipoIdentificacionMapeador tipoIdentificacionMapeador;

    private TipoIdentificacionRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new TipoIdentificacionRepositorioConsultaImplementacion(tipoIdentificacionDAO, tipoIdentificacionMapeador);
    }

    @Test
    void deberiaConsultarDTOs() {
        List<TipoIdentificacionEntidad> entidades = List.of(new TipoIdentificacionEntidad());
        when(tipoIdentificacionDAO.findAll()).thenReturn(entidades);
        when(tipoIdentificacionMapeador.construirDTOs(entidades)).thenReturn(List.of(new TipoIdentificacionDTO()));

        List<TipoIdentificacionDTO> resultado = repositorio.consultarDTOs();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarPorIdentificadorExistente() {
        UUID id = UUID.randomUUID();
        TipoIdentificacionEntidad entidad = new TipoIdentificacionEntidad();
        TipoIdentificacion modelo = mock(TipoIdentificacion.class);
        when(tipoIdentificacionDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(tipoIdentificacionMapeador.construirModelo(entidad)).thenReturn(modelo);

        TipoIdentificacion resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoIdentificadorNoExiste() {
        UUID id = UUID.randomUUID();
        when(tipoIdentificacionDAO.findById(id)).thenReturn(Optional.empty());

        TipoIdentificacion resultado = repositorio.consultarPorIdentificador(id);

        assertNull(resultado);
    }

    @Test
    void deberiaVerificarHayDatos() {
        when(tipoIdentificacionDAO.count()).thenReturn(5L);

        assertTrue(repositorio.hayDatos());
    }

    @Test
    void deberiaVerificarNoHayDatos() {
        when(tipoIdentificacionDAO.count()).thenReturn(0L);

        assertFalse(repositorio.hayDatos());
    }

    @Test
    void deberiaConsultarPorSiglaExistente() {
        TipoIdentificacionEntidad entidad = new TipoIdentificacionEntidad();
        TipoIdentificacion modelo = mock(TipoIdentificacion.class);
        when(tipoIdentificacionDAO.findBySigla("CC")).thenReturn(entidad);
        when(tipoIdentificacionMapeador.construirModelo(entidad)).thenReturn(modelo);

        TipoIdentificacion resultado = repositorio.consultarPorSigla("CC");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoSiglaNoExiste() {
        when(tipoIdentificacionDAO.findBySigla("XX")).thenReturn(null);

        TipoIdentificacion resultado = repositorio.consultarPorSigla("XX");

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarPorDescripcionExistente() {
        TipoIdentificacionEntidad entidad = new TipoIdentificacionEntidad();
        TipoIdentificacion modelo = mock(TipoIdentificacion.class);
        when(tipoIdentificacionDAO.findByDescripcion("Cedula")).thenReturn(entidad);
        when(tipoIdentificacionMapeador.construirModelo(entidad)).thenReturn(modelo);

        TipoIdentificacion resultado = repositorio.consultarPorDescripcion("Cedula");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoDescripcionNoExiste() {
        when(tipoIdentificacionDAO.findByDescripcion("NoExiste")).thenReturn(null);

        TipoIdentificacion resultado = repositorio.consultarPorDescripcion("NoExiste");

        assertNull(resultado);
    }
}
