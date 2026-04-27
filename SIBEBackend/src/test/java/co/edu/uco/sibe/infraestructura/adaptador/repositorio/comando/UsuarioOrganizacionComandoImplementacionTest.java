package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.infraestructura.adaptador.dao.*;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.*;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.UsuarioOrganizacionMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioOrganizacionComandoImplementacionTest {

    @Mock private UsuarioDAO usuarioDAO;
    @Mock private PersonaDAO personaDAO;
    @Mock private DireccionDAO direccionDAO;
    @Mock private AreaDAO areaDAO;
    @Mock private SubareaDAO subareaDAO;
    @Mock private UsuarioOrganizacionDAO usuarioOrganizacionDAO;
    @Mock private UsuarioOrganizacionMapeador usuarioOrganizacionMapeador;

    private UsuarioOrganizacionComandoImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new UsuarioOrganizacionComandoImplementacion(
                usuarioDAO, personaDAO, direccionDAO, areaDAO, subareaDAO,
                usuarioOrganizacionDAO, usuarioOrganizacionMapeador
        );
    }

    @Test
    void deberiaVincularUsuarioConDireccion() {
        UUID usuarioId = UUID.randomUUID();
        UUID direccionId = UUID.randomUUID();
        UUID resultadoId = UUID.randomUUID();

        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        DireccionEntidad direccionEntidad = new DireccionEntidad();
        UsuarioOrganizacionEntidad entidad = new UsuarioOrganizacionEntidad();
        entidad.setIdentificador(resultadoId);

        when(usuarioDAO.findById(usuarioId)).thenReturn(Optional.of(usuarioEntidad));
        when(direccionDAO.findById(direccionId)).thenReturn(Optional.of(direccionEntidad));
        when(usuarioOrganizacionMapeador.construirEntidadVinculadaConDireccion(usuarioEntidad, direccionEntidad)).thenReturn(entidad);
        when(usuarioOrganizacionDAO.save(entidad)).thenReturn(entidad);

        UUID resultado = repositorio.vincularUsuarioConDireccion(usuarioId, direccionId);

        assertEquals(resultadoId, resultado);
    }

    @Test
    void deberiaCambiarVinculacionUsuarioConDireccion() {
        UUID personaId = UUID.randomUUID();
        UUID direccionId = UUID.randomUUID();
        UUID resultadoId = UUID.randomUUID();

        PersonaEntidad personaEntidad = new PersonaEntidad();
        personaEntidad.setCorreo("test@test.com");
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        DireccionEntidad direccionEntidad = new DireccionEntidad();
        UsuarioOrganizacionEntidad entidad = new UsuarioOrganizacionEntidad();
        entidad.setIdentificador(resultadoId);

        when(personaDAO.findById(personaId)).thenReturn(Optional.of(personaEntidad));
        when(usuarioDAO.findByCorreo("test@test.com")).thenReturn(usuarioEntidad);
        when(direccionDAO.findById(direccionId)).thenReturn(Optional.of(direccionEntidad));
        when(usuarioOrganizacionDAO.findByUsuario(usuarioEntidad)).thenReturn(entidad);
        when(usuarioOrganizacionDAO.save(entidad)).thenReturn(entidad);

        UUID resultado = repositorio.cambiarVinculacionUsuarioConDireccion(personaId, direccionId);

        assertEquals(resultadoId, resultado);
        verify(usuarioOrganizacionMapeador).cambiarEntidadVinculadaConDireccion(entidad, direccionEntidad);
    }

    @Test
    void deberiaVincularUsuarioConArea() {
        UUID usuarioId = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        UUID resultadoId = UUID.randomUUID();

        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        AreaEntidad areaEntidad = new AreaEntidad();
        UsuarioOrganizacionEntidad entidad = new UsuarioOrganizacionEntidad();
        entidad.setIdentificador(resultadoId);

        when(usuarioDAO.findById(usuarioId)).thenReturn(Optional.of(usuarioEntidad));
        when(areaDAO.findById(areaId)).thenReturn(Optional.of(areaEntidad));
        when(usuarioOrganizacionMapeador.construirEntidadVinculadaConArea(usuarioEntidad, areaEntidad)).thenReturn(entidad);
        when(usuarioOrganizacionDAO.save(entidad)).thenReturn(entidad);

        UUID resultado = repositorio.vincularUsuarioConArea(usuarioId, areaId);

        assertEquals(resultadoId, resultado);
    }

    @Test
    void deberiaCambiarVinculacionUsuarioConArea() {
        UUID personaId = UUID.randomUUID();
        UUID areaId = UUID.randomUUID();
        UUID resultadoId = UUID.randomUUID();

        PersonaEntidad personaEntidad = new PersonaEntidad();
        personaEntidad.setCorreo("test@test.com");
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        AreaEntidad areaEntidad = new AreaEntidad();
        UsuarioOrganizacionEntidad entidad = new UsuarioOrganizacionEntidad();
        entidad.setIdentificador(resultadoId);

        when(personaDAO.findById(personaId)).thenReturn(Optional.of(personaEntidad));
        when(usuarioDAO.findByCorreo("test@test.com")).thenReturn(usuarioEntidad);
        when(areaDAO.findById(areaId)).thenReturn(Optional.of(areaEntidad));
        when(usuarioOrganizacionDAO.findByUsuario(usuarioEntidad)).thenReturn(entidad);
        when(usuarioOrganizacionDAO.save(entidad)).thenReturn(entidad);

        UUID resultado = repositorio.cambiarVinculacionUsuarioConArea(personaId, areaId);

        assertEquals(resultadoId, resultado);
        verify(usuarioOrganizacionMapeador).cambiarEntidadVinculadaConArea(entidad, areaEntidad);
    }

    @Test
    void deberiaVincularUsuarioConSubarea() {
        UUID usuarioId = UUID.randomUUID();
        UUID subareaId = UUID.randomUUID();
        UUID resultadoId = UUID.randomUUID();

        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        SubareaEntidad subareaEntidad = new SubareaEntidad();
        UsuarioOrganizacionEntidad entidad = new UsuarioOrganizacionEntidad();
        entidad.setIdentificador(resultadoId);

        when(usuarioDAO.findById(usuarioId)).thenReturn(Optional.of(usuarioEntidad));
        when(subareaDAO.findById(subareaId)).thenReturn(Optional.of(subareaEntidad));
        when(usuarioOrganizacionMapeador.construirEntidadVinculadaConSubarea(usuarioEntidad, subareaEntidad)).thenReturn(entidad);
        when(usuarioOrganizacionDAO.save(entidad)).thenReturn(entidad);

        UUID resultado = repositorio.vincularUsuarioConSubarea(usuarioId, subareaId);

        assertEquals(resultadoId, resultado);
    }

    @Test
    void deberiaCambiarVinculacionUsuarioConSubarea() {
        UUID personaId = UUID.randomUUID();
        UUID subareaId = UUID.randomUUID();
        UUID resultadoId = UUID.randomUUID();

        PersonaEntidad personaEntidad = new PersonaEntidad();
        personaEntidad.setCorreo("test@test.com");
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        SubareaEntidad subareaEntidad = new SubareaEntidad();
        UsuarioOrganizacionEntidad entidad = new UsuarioOrganizacionEntidad();
        entidad.setIdentificador(resultadoId);

        when(personaDAO.findById(personaId)).thenReturn(Optional.of(personaEntidad));
        when(usuarioDAO.findByCorreo("test@test.com")).thenReturn(usuarioEntidad);
        when(subareaDAO.findById(subareaId)).thenReturn(Optional.of(subareaEntidad));
        when(usuarioOrganizacionDAO.findByUsuario(usuarioEntidad)).thenReturn(entidad);
        when(usuarioOrganizacionDAO.save(entidad)).thenReturn(entidad);

        UUID resultado = repositorio.cambiarVinculacionUsuarioConSubarea(personaId, subareaId);

        assertEquals(resultadoId, resultado);
        verify(usuarioOrganizacionMapeador).cambiarEntidadVinculadaConArea(entidad, subareaEntidad);
    }
}
