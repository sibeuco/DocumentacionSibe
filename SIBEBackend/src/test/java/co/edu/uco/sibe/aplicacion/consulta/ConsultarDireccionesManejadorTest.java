package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.ContextoUsuarioAutenticado;
import co.edu.uco.sibe.dominio.dto.DireccionDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.ADMINISTRADOR_DIRECCION;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.COLABORADOR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarDireccionesManejadorTest {

    @Mock private DireccionRepositorioConsulta direccionRepositorioConsulta;
    @Mock private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ConsultarDireccionesManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarDireccionesManejador(direccionRepositorioConsulta, autorizacionServicio);
    }

    @Test
    void deberiaRetornarTodasLasDireccionesParaAdminDireccion() {
        List<DireccionDTO> esperado = List.of(mock(DireccionDTO.class));
        ContextoUsuarioAutenticado contexto = mock(ContextoUsuarioAutenticado.class);

        when(direccionRepositorioConsulta.consultarDTOs()).thenReturn(esperado);
        when(autorizacionServicio.obtenerContexto()).thenReturn(contexto);
        when(contexto.getRol()).thenReturn(ADMINISTRADOR_DIRECCION);

        List<DireccionDTO> resultado = manejador.ejecutar();

        assertEquals(esperado, resultado);
        verify(direccionRepositorioConsulta).consultarDTOs();
    }

    @Test
    void deberiaFiltrarDireccionesPorIdParaNoAdmin() {
        UUID direccionId = UUID.randomUUID();
        DireccionDTO direccionPropia = mock(DireccionDTO.class);
        DireccionDTO direccionOtra = mock(DireccionDTO.class);
        when(direccionPropia.getIdentificador()).thenReturn(direccionId.toString());
        when(direccionOtra.getIdentificador()).thenReturn(UUID.randomUUID().toString());

        ContextoUsuarioAutenticado contexto = mock(ContextoUsuarioAutenticado.class);

        when(direccionRepositorioConsulta.consultarDTOs()).thenReturn(List.of(direccionPropia, direccionOtra));
        when(autorizacionServicio.obtenerContexto()).thenReturn(contexto);
        when(contexto.getRol()).thenReturn(COLABORADOR);
        when(contexto.getDireccionId()).thenReturn(direccionId);

        List<DireccionDTO> resultado = manejador.ejecutar();

        assertEquals(1, resultado.size());
        assertEquals(direccionId.toString(), resultado.get(0).getIdentificador());
    }
}
