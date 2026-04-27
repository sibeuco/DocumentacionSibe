package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.ContextoUsuarioAutenticado;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.ADMINISTRADOR_DIRECCION;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.ADMINISTRADOR_AREA;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.COLABORADOR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarSubareasManejadorTest {

    @Mock private SubareaRepositorioConsulta subareaRepositorioConsulta;
    @Mock private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ConsultarSubareasManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarSubareasManejador(subareaRepositorioConsulta, autorizacionServicio);
    }

    @Test
    void deberiaRetornarTodasLasSubareasParaAdminDireccion() {
        List<Subarea> esperado = List.of(mock(Subarea.class));
        ContextoUsuarioAutenticado contexto = mock(ContextoUsuarioAutenticado.class);

        when(subareaRepositorioConsulta.consultarTodos()).thenReturn(esperado);
        when(autorizacionServicio.obtenerContexto()).thenReturn(contexto);
        when(contexto.getRol()).thenReturn(ADMINISTRADOR_DIRECCION);

        List<Subarea> resultado = manejador.ejecutar();

        assertEquals(esperado, resultado);
        verify(subareaRepositorioConsulta).consultarTodos();
    }

    @Test
    void deberiaRetornarTodasLasSubareasParaAdminArea() {
        List<Subarea> esperado = List.of(mock(Subarea.class));
        ContextoUsuarioAutenticado contexto = mock(ContextoUsuarioAutenticado.class);

        when(subareaRepositorioConsulta.consultarTodos()).thenReturn(esperado);
        when(autorizacionServicio.obtenerContexto()).thenReturn(contexto);
        when(contexto.getRol()).thenReturn(ADMINISTRADOR_AREA);

        List<Subarea> resultado = manejador.ejecutar();

        assertEquals(esperado, resultado);
    }

    @Test
    void deberiaFiltrarSubareasParaColaborador() {
        UUID subareaId = UUID.randomUUID();
        UUID otraId = UUID.randomUUID();
        Subarea subareaPropia = mock(Subarea.class);
        Subarea subareaOtra = mock(Subarea.class);
        when(subareaPropia.getIdentificador()).thenReturn(subareaId);
        when(subareaOtra.getIdentificador()).thenReturn(otraId);

        ContextoUsuarioAutenticado contexto = mock(ContextoUsuarioAutenticado.class);

        when(subareaRepositorioConsulta.consultarTodos()).thenReturn(List.of(subareaPropia, subareaOtra));
        when(autorizacionServicio.obtenerContexto()).thenReturn(contexto);
        when(contexto.getRol()).thenReturn(COLABORADOR);
        when(contexto.getSubareaId()).thenReturn(subareaId);

        List<Subarea> resultado = manejador.ejecutar();

        assertEquals(1, resultado.size());
        assertEquals(subareaId, resultado.get(0).getIdentificador());
    }
}
