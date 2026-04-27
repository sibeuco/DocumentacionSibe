package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.AreaDTO;
import co.edu.uco.sibe.dominio.dto.ContextoUsuarioAutenticado;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
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
class ConsultarAreasManejadorTest {

    @Mock private AreaRepositorioConsulta areaRepositorioConsulta;
    @Mock private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ConsultarAreasManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarAreasManejador(areaRepositorioConsulta, autorizacionServicio);
    }

    @Test
    void deberiaRetornarTodasLasAreasParaAdminDireccion() {
        List<AreaDTO> esperado = List.of(mock(AreaDTO.class));
        ContextoUsuarioAutenticado contexto = mock(ContextoUsuarioAutenticado.class);

        when(areaRepositorioConsulta.consultarDTOs()).thenReturn(esperado);
        when(autorizacionServicio.obtenerContexto()).thenReturn(contexto);
        when(contexto.getRol()).thenReturn(ADMINISTRADOR_DIRECCION);

        List<AreaDTO> resultado = manejador.ejecutar();

        assertEquals(esperado, resultado);
        verify(areaRepositorioConsulta).consultarDTOs();
    }

    @Test
    void deberiaFiltrarAreasPorIdParaAdminArea() {
        UUID areaId = UUID.randomUUID();
        AreaDTO areaPropia = mock(AreaDTO.class);
        AreaDTO areaOtra = mock(AreaDTO.class);
        when(areaPropia.getIdentificador()).thenReturn(areaId.toString());
        when(areaOtra.getIdentificador()).thenReturn(UUID.randomUUID().toString());

        ContextoUsuarioAutenticado contexto = mock(ContextoUsuarioAutenticado.class);

        when(areaRepositorioConsulta.consultarDTOs()).thenReturn(List.of(areaPropia, areaOtra));
        when(autorizacionServicio.obtenerContexto()).thenReturn(contexto);
        when(contexto.getRol()).thenReturn(ADMINISTRADOR_AREA);
        when(contexto.getAreaId()).thenReturn(areaId);

        List<AreaDTO> resultado = manejador.ejecutar();

        assertEquals(1, resultado.size());
        assertEquals(areaId.toString(), resultado.get(0).getIdentificador());
    }

    @Test
    void deberiaRetornarListaVaciaParaColaboradorConSubareaSinArea() {
        ContextoUsuarioAutenticado contexto = mock(ContextoUsuarioAutenticado.class);

        when(areaRepositorioConsulta.consultarDTOs()).thenReturn(List.of(mock(AreaDTO.class)));
        when(autorizacionServicio.obtenerContexto()).thenReturn(contexto);
        when(contexto.getRol()).thenReturn(COLABORADOR);
        when(contexto.getAreaId()).thenReturn(null);

        List<AreaDTO> resultado = manejador.ejecutar();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deberiaRetornarListaVaciaParaColaboradorSinAreaNiSubarea() {
        ContextoUsuarioAutenticado contexto = mock(ContextoUsuarioAutenticado.class);

        when(areaRepositorioConsulta.consultarDTOs()).thenReturn(List.of(mock(AreaDTO.class)));
        when(autorizacionServicio.obtenerContexto()).thenReturn(contexto);
        when(contexto.getRol()).thenReturn(COLABORADOR);
        when(contexto.getAreaId()).thenReturn(null);

        List<AreaDTO> resultado = manejador.ejecutar();

        assertTrue(resultado.isEmpty());
    }
}
