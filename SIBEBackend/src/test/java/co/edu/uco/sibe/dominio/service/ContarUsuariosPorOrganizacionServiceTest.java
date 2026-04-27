package co.edu.uco.sibe.dominio.service;

import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.puerto.consulta.UsuarioOrganizacionRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContarUsuariosPorOrganizacionServiceTest {

    @Mock
    private UsuarioOrganizacionRepositorioConsulta usuarioOrganizacionRepositorioConsulta;
    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ContarUsuariosPorOrganizacionService service;

    @BeforeEach
    void setUp() {
        service = new ContarUsuariosPorOrganizacionService(usuarioOrganizacionRepositorioConsulta,
                autorizacionServicio);
    }

    @Test
    void deberiaContarUsuariosPorDireccionSinAreas() {
        UUID direccionId = UUID.randomUUID();
        Direccion direccion = mock(Direccion.class);
        when(direccion.getIdentificador()).thenReturn(direccionId);
        when(direccion.getAreas()).thenReturn(Collections.emptyList());

        doNothing().when(autorizacionServicio).validarAccesoADireccion(direccionId);
        when(usuarioOrganizacionRepositorioConsulta.contarPorDireccion(direccionId)).thenReturn(5L);

        long resultado = service.contarUsuariosPorDireccion(direccion);

        assertEquals(5L, resultado);
    }

    @Test
    void deberiaContarUsuariosPorDireccionConAreas() {
        UUID direccionId = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        Direccion direccion = mock(Direccion.class);
        Area area = mock(Area.class);

        when(direccion.getIdentificador()).thenReturn(direccionId);
        when(direccion.getAreas()).thenReturn(List.of(area));
        when(area.getIdentificador()).thenReturn(areaId);
        when(area.getSubareas()).thenReturn(Collections.emptyList());

        doNothing().when(autorizacionServicio).validarAccesoADireccion(direccionId);
        when(usuarioOrganizacionRepositorioConsulta.contarPorDireccion(direccionId)).thenReturn(3L);
        when(usuarioOrganizacionRepositorioConsulta.contarPorArea(areaId)).thenReturn(2L);

        long resultado = service.contarUsuariosPorDireccion(direccion);

        assertEquals(5L, resultado);
    }

    @Test
    void deberiaContarUsuariosPorAreaConSubareas() {
        UUID areaId = UUID.randomUUID();
        UUID subareaId = UUID.randomUUID();
        Area area = mock(Area.class);
        Subarea subarea = mock(Subarea.class);

        when(area.getIdentificador()).thenReturn(areaId);
        when(area.getSubareas()).thenReturn(List.of(subarea));
        when(subarea.getIdentificador()).thenReturn(subareaId);

        doNothing().when(autorizacionServicio).validarAccesoAArea(areaId);
        when(usuarioOrganizacionRepositorioConsulta.contarPorArea(areaId)).thenReturn(3L);
        when(usuarioOrganizacionRepositorioConsulta.contarPorSubarea(subareaId)).thenReturn(2L);

        long resultado = service.contarUsuariosPorArea(area);

        assertEquals(5L, resultado);
    }

    @Test
    void deberiaContarUsuariosPorSubarea() {
        UUID subareaId = UUID.randomUUID();
        Subarea subarea = mock(Subarea.class);
        when(subarea.getIdentificador()).thenReturn(subareaId);

        doNothing().when(autorizacionServicio).validarAccesoASubarea(subareaId);
        when(usuarioOrganizacionRepositorioConsulta.contarPorSubarea(subareaId)).thenReturn(7L);

        long resultado = service.contarUsuariosPorSubarea(subarea);

        assertEquals(7L, resultado);
    }

    @Test
    void deberiaLanzarExcepcionCuandoNoTieneAccesoADireccion() {
        UUID direccionId = UUID.randomUUID();
        Direccion direccion = mock(Direccion.class);
        when(direccion.getIdentificador()).thenReturn(direccionId);

        doThrow(new AuthorizationException("No tiene acceso")).when(autorizacionServicio)
                .validarAccesoADireccion(direccionId);

        assertThrows(AuthorizationException.class,
                () -> service.contarUsuariosPorDireccion(direccion));
    }
}
