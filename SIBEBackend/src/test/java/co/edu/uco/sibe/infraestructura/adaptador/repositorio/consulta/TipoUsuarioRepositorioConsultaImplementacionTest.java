package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.TipoUsuarioDTO;
import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.infraestructura.adaptador.dao.TipoUsuarioDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.TipoUsuarioEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.TipoUsuarioMapeador;
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
class TipoUsuarioRepositorioConsultaImplementacionTest {

    @Mock private TipoUsuarioDAO tipoUsuarioDAO;
    @Mock private TipoUsuarioMapeador tipoUsuarioMapeador;

    private TipoUsuarioRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new TipoUsuarioRepositorioConsultaImplementacion(tipoUsuarioDAO, tipoUsuarioMapeador);
    }

    @Test
    void deberiaConsultarDTOs() {
        List<TipoUsuarioEntidad> entidades = List.of(new TipoUsuarioEntidad());
        when(tipoUsuarioDAO.findAll()).thenReturn(entidades);
        when(tipoUsuarioMapeador.construirDTOs(entidades)).thenReturn(List.of(new TipoUsuarioDTO()));

        List<TipoUsuarioDTO> resultado = repositorio.consultarDTOs();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaConsultarPorIdentificadorExistente() {
        UUID id = UUID.randomUUID();
        TipoUsuarioEntidad entidad = new TipoUsuarioEntidad();
        TipoUsuario modelo = mock(TipoUsuario.class);
        when(tipoUsuarioDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(tipoUsuarioMapeador.construirModelo(entidad)).thenReturn(modelo);

        TipoUsuario resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoIdentificadorNoExiste() {
        UUID id = UUID.randomUUID();
        when(tipoUsuarioDAO.findById(id)).thenReturn(Optional.empty());

        TipoUsuario resultado = repositorio.consultarPorIdentificador(id);

        assertNull(resultado);
    }

    @Test
    void deberiaVerificarHayDatos() {
        when(tipoUsuarioDAO.count()).thenReturn(5L);

        assertTrue(repositorio.hayDatos());
    }

    @Test
    void deberiaVerificarNoHayDatos() {
        when(tipoUsuarioDAO.count()).thenReturn(0L);

        assertFalse(repositorio.hayDatos());
    }

    @Test
    void deberiaConsultarPorCodigoExistente() {
        TipoUsuarioEntidad entidad = new TipoUsuarioEntidad();
        TipoUsuario modelo = mock(TipoUsuario.class);
        when(tipoUsuarioDAO.findFirstByCodigo("ADM")).thenReturn(entidad);
        when(tipoUsuarioMapeador.construirModelo(entidad)).thenReturn(modelo);

        TipoUsuario resultado = repositorio.consultarPorCodigo("ADM");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoCodigoNoExiste() {
        when(tipoUsuarioDAO.findFirstByCodigo("XX")).thenReturn(null);

        TipoUsuario resultado = repositorio.consultarPorCodigo("XX");

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarPorNombreExistente() {
        TipoUsuarioEntidad entidad = new TipoUsuarioEntidad();
        TipoUsuario modelo = mock(TipoUsuario.class);
        when(tipoUsuarioDAO.findByNombre("Admin")).thenReturn(entidad);
        when(tipoUsuarioMapeador.construirModelo(entidad)).thenReturn(modelo);

        TipoUsuario resultado = repositorio.consultarPorNombre("Admin");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoNombreNoExiste() {
        when(tipoUsuarioDAO.findByNombre("NoExiste")).thenReturn(null);

        TipoUsuario resultado = repositorio.consultarPorNombre("NoExiste");

        assertNull(resultado);
    }
}
