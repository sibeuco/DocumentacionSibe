package co.edu.uco.sibe.infraestructura.adaptador.servicio;

import co.edu.uco.sibe.dominio.dto.ContextoUsuarioAutenticado;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContextoUsuarioProveedorServicioImplTest {

    private ContextoUsuarioProveedorServicioImpl servicio;

    @BeforeEach
    void setUp() {
        servicio = new ContextoUsuarioProveedorServicioImpl();
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void deberiaRetornarContextoCuandoUsuarioAutenticado() {
        ContextoUsuarioAutenticado contexto = new ContextoUsuarioAutenticado();
        var auth = new UsernamePasswordAuthenticationToken("user", "pass", List.of());
        auth.setDetails(contexto);
        SecurityContextHolder.getContext().setAuthentication(auth);

        ContextoUsuarioAutenticado resultado = servicio.obtenerContextoActual();

        assertNotNull(resultado);
        assertEquals(contexto, resultado);
    }

    @Test
    void deberiaLanzarSecurityExceptionCuandoNoHayAutenticacion() {
        assertThrows(SecurityException.class, () -> servicio.obtenerContextoActual());
    }

    @Test
    void deberiaLanzarSecurityExceptionCuandoDetailsNoEsContexto() {
        var auth = new UsernamePasswordAuthenticationToken("user", "pass", List.of());
        auth.setDetails("not-a-contexto");
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertThrows(SecurityException.class, () -> servicio.obtenerContextoActual());
    }
}
