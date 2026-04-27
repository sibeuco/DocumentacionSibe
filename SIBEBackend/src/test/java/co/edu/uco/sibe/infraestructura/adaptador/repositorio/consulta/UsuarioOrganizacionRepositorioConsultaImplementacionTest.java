package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.infraestructura.adaptador.dao.UsuarioOrganizacionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.AreaEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.SubareaEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.UsuarioOrganizacionEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioOrganizacionRepositorioConsultaImplementacionTest {

    @Mock private UsuarioOrganizacionDAO usuarioOrganizacionDAO;

    private UsuarioOrganizacionRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new UsuarioOrganizacionRepositorioConsultaImplementacion(usuarioOrganizacionDAO);
    }

    @Test
    void deberiaContarPorDireccion() {
        UUID direccionId = UUID.randomUUID();
        when(usuarioOrganizacionDAO.countByDireccionIdentificador(direccionId)).thenReturn(5L);

        assertEquals(5L, repositorio.contarPorDireccion(direccionId));
    }

    @Test
    void deberiaContarPorArea() {
        UUID areaId = UUID.randomUUID();
        when(usuarioOrganizacionDAO.countByAreaIdentificador(areaId)).thenReturn(3L);

        assertEquals(3L, repositorio.contarPorArea(areaId));
    }

    @Test
    void deberiaContarPorSubarea() {
        UUID subareaId = UUID.randomUUID();
        when(usuarioOrganizacionDAO.countBySubareaIdentificador(subareaId)).thenReturn(2L);

        assertEquals(2L, repositorio.contarPorSubarea(subareaId));
    }

    @Test
    void deberiaConsultarAreaIdPorUsuarioId() {
        UUID usuarioId = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        AreaEntidad areaEntidad = new AreaEntidad();
        areaEntidad.setIdentificador(areaId);
        UsuarioOrganizacionEntidad entidad = new UsuarioOrganizacionEntidad();
        entidad.setArea(areaEntidad);

        when(usuarioOrganizacionDAO.findByUsuarioIdentificador(usuarioId)).thenReturn(entidad);

        UUID resultado = repositorio.consultarAreaIdPorUsuarioId(usuarioId);

        assertEquals(areaId, resultado);
    }

    @Test
    void deberiaRetornarNullCuandoUsuarioNoTieneOrganizacion() {
        UUID usuarioId = UUID.randomUUID();
        when(usuarioOrganizacionDAO.findByUsuarioIdentificador(usuarioId)).thenReturn(null);

        assertNull(repositorio.consultarAreaIdPorUsuarioId(usuarioId));
    }

    @Test
    void deberiaRetornarNullCuandoUsuarioNoTieneArea() {
        UUID usuarioId = UUID.randomUUID();
        UsuarioOrganizacionEntidad entidad = new UsuarioOrganizacionEntidad();
        entidad.setArea(null);

        when(usuarioOrganizacionDAO.findByUsuarioIdentificador(usuarioId)).thenReturn(entidad);

        assertNull(repositorio.consultarAreaIdPorUsuarioId(usuarioId));
    }

    @Test
    void deberiaConsultarSubareaIdPorUsuarioId() {
        UUID usuarioId = UUID.randomUUID();
        UUID subareaId = UUID.randomUUID();
        SubareaEntidad subareaEntidad = new SubareaEntidad();
        subareaEntidad.setIdentificador(subareaId);
        UsuarioOrganizacionEntidad entidad = new UsuarioOrganizacionEntidad();
        entidad.setSubarea(subareaEntidad);

        when(usuarioOrganizacionDAO.findByUsuarioIdentificador(usuarioId)).thenReturn(entidad);

        UUID resultado = repositorio.consultarSubareaIdPorUsuarioId(usuarioId);

        assertEquals(subareaId, resultado);
    }

    @Test
    void deberiaRetornarNullCuandoUsuarioNoTieneSubarea() {
        UUID usuarioId = UUID.randomUUID();
        UsuarioOrganizacionEntidad entidad = new UsuarioOrganizacionEntidad();
        entidad.setSubarea(null);

        when(usuarioOrganizacionDAO.findByUsuarioIdentificador(usuarioId)).thenReturn(entidad);

        assertNull(repositorio.consultarSubareaIdPorUsuarioId(usuarioId));
    }

    @Test
    void deberiaRetornarNullEnSubareaIdCuandoUsuarioNoTieneOrganizacion() {
        UUID usuarioId = UUID.randomUUID();
        when(usuarioOrganizacionDAO.findByUsuarioIdentificador(usuarioId)).thenReturn(null);

        assertNull(repositorio.consultarSubareaIdPorUsuarioId(usuarioId));
    }
}
