package co.edu.uco.sibe.dominio.modelo;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioOrganizacionTest {

    @Test
    void deberiaConstruirConParametros() {
        UUID id = UUID.randomUUID();
        Usuario usuario = Usuario.construir(UUID.randomUUID(), "test@correo.com", "Clave123!", TipoUsuario.construir(), true);
        Direccion direccion = Direccion.construir(UUID.randomUUID(), "Direccion de Bienestar", new ArrayList<>(), new ArrayList<>());
        Area area = Area.construir(UUID.randomUUID(), "Area de Salud", new ArrayList<>(), new ArrayList<>());
        Subarea subarea = Subarea.construir(UUID.randomUUID(), "Salud Oral", new ArrayList<>());

        UsuarioOrganizacion uo = UsuarioOrganizacion.construir(id, usuario, direccion, area, subarea);

        assertEquals(id, uo.getIdentificador());
        assertEquals(usuario, uo.getUsuario());
        assertEquals(direccion, uo.getDireccion());
        assertEquals(area, uo.getArea());
        assertEquals(subarea, uo.getSubarea());
    }

    @Test
    void deberiaConstruirConValoresPorDefecto() {
        UsuarioOrganizacion uo = UsuarioOrganizacion.construir();

        assertNotNull(uo.getIdentificador());
        assertNotNull(uo.getUsuario());
        assertNotNull(uo.getDireccion());
        assertNotNull(uo.getArea());
        assertNotNull(uo.getSubarea());
    }
}
