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
class ModificarVinculacionUsuarioConAreaServiceTest {

    @Mock
    private UsuarioOrganizacionComando usuarioOrganizacionComando;

    private ModificarVinculacionUsuarioConAreaService service;

    @BeforeEach
    void setUp() {
        service = new ModificarVinculacionUsuarioConAreaService(usuarioOrganizacionComando);
    }

    @Test
    void deberiaCambiarVinculacionConDireccion() {
        UUID usuario = UUID.randomUUID();
        UUID area = UUID.randomUUID();
        UUID resultado = UUID.randomUUID();

        when(usuarioOrganizacionComando.cambiarVinculacionUsuarioConDireccion(usuario, area)).thenReturn(resultado);

        assertEquals(resultado, service.ejecutar(usuario, area, TipoArea.DIRECCION));
    }

    @Test
    void deberiaCambiarVinculacionConArea() {
        UUID usuario = UUID.randomUUID();
        UUID area = UUID.randomUUID();
        UUID resultado = UUID.randomUUID();

        when(usuarioOrganizacionComando.cambiarVinculacionUsuarioConArea(usuario, area)).thenReturn(resultado);

        assertEquals(resultado, service.ejecutar(usuario, area, TipoArea.AREA));
    }

    @Test
    void deberiaCambiarVinculacionConSubarea() {
        UUID usuario = UUID.randomUUID();
        UUID area = UUID.randomUUID();
        UUID resultado = UUID.randomUUID();

        when(usuarioOrganizacionComando.cambiarVinculacionUsuarioConSubarea(usuario, area)).thenReturn(resultado);

        assertEquals(resultado, service.ejecutar(usuario, area, TipoArea.SUBAREA));
    }
}
