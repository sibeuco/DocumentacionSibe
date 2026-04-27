package co.edu.uco.sibe.dominio.service;

import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.puerto.comando.UsuarioOrganizacionComando;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VincularUsuarioConAreaServiceTest {

    @Mock
    private UsuarioOrganizacionComando usuarioOrganizacionComando;

    private VincularUsuarioConAreaService service;

    @BeforeEach
    void setUp() {
        service = new VincularUsuarioConAreaService(usuarioOrganizacionComando);
    }

    @Test
    void deberiaVincularConDireccion() {
        UUID usuario = UUID.randomUUID();
        UUID area = UUID.randomUUID();
        UUID resultado = UUID.randomUUID();

        when(usuarioOrganizacionComando.vincularUsuarioConDireccion(usuario, area)).thenReturn(resultado);

        assertEquals(resultado, service.ejecutar(usuario, area, TipoArea.DIRECCION));
        verify(usuarioOrganizacionComando).vincularUsuarioConDireccion(usuario, area);
    }

    @Test
    void deberiaVincularConArea() {
        UUID usuario = UUID.randomUUID();
        UUID area = UUID.randomUUID();
        UUID resultado = UUID.randomUUID();

        when(usuarioOrganizacionComando.vincularUsuarioConArea(usuario, area)).thenReturn(resultado);

        assertEquals(resultado, service.ejecutar(usuario, area, TipoArea.AREA));
        verify(usuarioOrganizacionComando).vincularUsuarioConArea(usuario, area);
    }

    @Test
    void deberiaVincularConSubarea() {
        UUID usuario = UUID.randomUUID();
        UUID area = UUID.randomUUID();
        UUID resultado = UUID.randomUUID();

        when(usuarioOrganizacionComando.vincularUsuarioConSubarea(usuario, area)).thenReturn(resultado);

        assertEquals(resultado, service.ejecutar(usuario, area, TipoArea.SUBAREA));
        verify(usuarioOrganizacionComando).vincularUsuarioConSubarea(usuario, area);
    }
}
