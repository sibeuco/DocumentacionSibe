package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.ContarUsuariosPorOrganizacionService;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContarUsuariosPorOrganizacionManejadorTest {

    @Mock private DireccionRepositorioConsulta direccionRepositorioConsulta;
    @Mock private AreaRepositorioConsulta areaRepositorioConsulta;
    @Mock private SubareaRepositorioConsulta subareaRepositorioConsulta;
    @Mock private ContarUsuariosPorOrganizacionService contarUsuariosPorOrganizacionService;

    private ContarUsuariosPorOrganizacionManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ContarUsuariosPorOrganizacionManejador(
                direccionRepositorioConsulta,
                areaRepositorioConsulta,
                subareaRepositorioConsulta,
                contarUsuariosPorOrganizacionService
        );
    }

    @Test
    void deberiaContarUsuariosPorDireccion() {
        UUID id = UUID.randomUUID();
        Direccion direccion = mock(Direccion.class);

        when(direccionRepositorioConsulta.consultarPorIdentificador(id)).thenReturn(direccion);
        when(contarUsuariosPorOrganizacionService.contarUsuariosPorDireccion(direccion)).thenReturn(10L);

        ComandoRespuesta<Long> resultado = manejador.ejecutar(id.toString());

        assertEquals(10L, resultado.getValor());
        verify(direccionRepositorioConsulta).consultarPorIdentificador(id);
        verify(contarUsuariosPorOrganizacionService).contarUsuariosPorDireccion(direccion);
    }

    @Test
    void deberiaContarUsuariosPorArea() {
        UUID id = UUID.randomUUID();
        Area area = mock(Area.class);

        when(direccionRepositorioConsulta.consultarPorIdentificador(id)).thenReturn(null);
        when(areaRepositorioConsulta.consultarPorIdentificador(id)).thenReturn(area);
        when(contarUsuariosPorOrganizacionService.contarUsuariosPorArea(area)).thenReturn(5L);

        ComandoRespuesta<Long> resultado = manejador.ejecutar(id.toString());

        assertEquals(5L, resultado.getValor());
        verify(areaRepositorioConsulta).consultarPorIdentificador(id);
    }

    @Test
    void deberiaContarUsuariosPorSubarea() {
        UUID id = UUID.randomUUID();
        Subarea subarea = mock(Subarea.class);

        when(direccionRepositorioConsulta.consultarPorIdentificador(id)).thenReturn(null);
        when(areaRepositorioConsulta.consultarPorIdentificador(id)).thenReturn(null);
        when(subareaRepositorioConsulta.consultarPorIdentificador(id)).thenReturn(subarea);
        when(contarUsuariosPorOrganizacionService.contarUsuariosPorSubarea(subarea)).thenReturn(3L);

        ComandoRespuesta<Long> resultado = manejador.ejecutar(id.toString());

        assertEquals(3L, resultado.getValor());
        verify(subareaRepositorioConsulta).consultarPorIdentificador(id);
    }

    @Test
    void deberiaLanzarExcepcionCuandoOrganizacionNoExiste() {
        UUID id = UUID.randomUUID();

        when(direccionRepositorioConsulta.consultarPorIdentificador(id)).thenReturn(null);
        when(areaRepositorioConsulta.consultarPorIdentificador(id)).thenReturn(null);
        when(subareaRepositorioConsulta.consultarPorIdentificador(id)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> manejador.ejecutar(id.toString()));
    }
}
