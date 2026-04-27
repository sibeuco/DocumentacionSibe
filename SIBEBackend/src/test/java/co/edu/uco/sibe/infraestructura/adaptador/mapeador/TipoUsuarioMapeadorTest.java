package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.TipoUsuarioDTO;
import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.TipoUsuarioEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.UsuarioTipoUsuarioEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TipoUsuarioMapeadorTest {

    private TipoUsuarioMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new TipoUsuarioMapeador();
    }

    @Test
    void deberiaConstruirDTO() {
        UUID id = UUID.randomUUID();
        TipoUsuarioEntidad entidad = new TipoUsuarioEntidad(id, "ADM", "Administrador", true, true, true, true);
        TipoUsuarioDTO dto = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), dto.getIdentificador());
        assertEquals("ADM", dto.getCodigo());
        assertEquals("Administrador", dto.getNombre());
    }

    @Test
    void deberiaConstruirDTOs() {
        TipoUsuarioEntidad e1 = new TipoUsuarioEntidad(UUID.randomUUID(), "ADM", "Admin", true, true, true, true);
        List<TipoUsuarioDTO> dtos = mapeador.construirDTOs(List.of(e1));
        assertEquals(1, dtos.size());
    }

    @Test
    void deberiaConstruirModelo() {
        UUID id = UUID.randomUUID();
        TipoUsuarioEntidad entidad = new TipoUsuarioEntidad(id, "ADM", "Administrador", true, false, true, false);
        TipoUsuario modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
        assertEquals("ADM", modelo.getCodigo());
        assertTrue(modelo.isCrear());
        assertFalse(modelo.isModificar());
    }

    @Test
    void deberiaConstruirEntidad() {
        UUID id = UUID.randomUUID();
        TipoUsuario modelo = TipoUsuario.construir(id, "ADM", "Administrador", true, true, true, true);
        TipoUsuarioEntidad entidad = mapeador.construirEntidad(modelo);

        assertEquals(id, entidad.getIdentificador());
        assertEquals("ADM", entidad.getCodigo());
    }

    @Test
    void deberiaModificarEntidad() {
        UsuarioTipoUsuarioEntidad utue = new UsuarioTipoUsuarioEntidad();
        TipoUsuario modelo = TipoUsuario.construir(UUID.randomUUID(), "USR", "Usuario", false, false, false, true);
        mapeador.modificarEntidad(utue, modelo);

        assertNotNull(utue.getTipoUsuario());
    }
}
