package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.ContextoUsuarioAutenticado;
import co.edu.uco.sibe.dominio.dto.UsuarioAreaDTO;
import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.testdatabuilder.ContextoUsuarioAutenticadoTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultarUsuariosUseCaseTest {

    @Mock
    private PersonaRepositorioConsulta personaRepositorioConsulta;

    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    @Mock
    private AreaRepositorioConsulta areaRepositorioConsulta;

    private ConsultarUsuariosUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarUsuariosUseCase(personaRepositorioConsulta, autorizacionServicio, areaRepositorioConsulta);
    }

    @Test
    void deberiaRetornarTodosLosUsuariosCuandoEsAdminDireccion() {
        UUID areaId = UUID.randomUUID();
        ContextoUsuarioAutenticado contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conRol(ADMINISTRADOR_DIRECCION).conAreaId(areaId).construir();

        UsuarioDTO usuario1 = crearUsuarioConArea(areaId);
        UsuarioDTO usuario2 = crearUsuarioConArea(UUID.randomUUID());

        when(personaRepositorioConsulta.consultarUsuariosDTO()).thenReturn(List.of(usuario1, usuario2));
        when(autorizacionServicio.obtenerContexto()).thenReturn(contexto);

        List<UsuarioDTO> resultado = useCase.ejecutar();

        assertEquals(2, resultado.size());
    }

    @Test
    void deberiaFiltrarUsuariosPorAreaYSubareasCuandoEsAdminArea() {
        UUID areaId = UUID.randomUUID();
        UUID subareaId = UUID.randomUUID();
        UUID otraAreaId = UUID.randomUUID();
        ContextoUsuarioAutenticado contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conRol(ADMINISTRADOR_AREA).conAreaId(areaId).construir();

        UsuarioDTO usuarioMismaArea = crearUsuarioConArea(areaId);
        UsuarioDTO usuarioSubarea = crearUsuarioConArea(subareaId);
        UsuarioDTO usuarioOtraArea = crearUsuarioConArea(otraAreaId);

        when(personaRepositorioConsulta.consultarUsuariosDTO()).thenReturn(List.of(usuarioMismaArea, usuarioSubarea, usuarioOtraArea));
        when(autorizacionServicio.obtenerContexto()).thenReturn(contexto);

        Subarea subarea = Subarea.construir(subareaId, "SubareaTest", null);
        Area area = Area.construir(areaId, "AreaTest", List.of(subarea), null);
        when(areaRepositorioConsulta.consultarPorIdentificador(areaId)).thenReturn(area);

        List<UsuarioDTO> resultado = useCase.ejecutar();

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().anyMatch(u -> u.getArea().getIdentificador().equals(areaId.toString())));
        assertTrue(resultado.stream().anyMatch(u -> u.getArea().getIdentificador().equals(subareaId.toString())));
        assertFalse(resultado.stream().anyMatch(u -> u.getArea().getIdentificador().equals(otraAreaId.toString())));
    }

    private UsuarioDTO crearUsuarioConArea(UUID areaId) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdentificador(UUID.randomUUID().toString());
        UsuarioAreaDTO area = new UsuarioAreaDTO();
        area.setIdentificador(areaId.toString());
        dto.setArea(area);
        return dto;
    }

    @Test
    void deberiaLanzarExcepcionCuandoNoHayUsuarios() {
        when(personaRepositorioConsulta.consultarUsuariosDTO()).thenReturn(List.of());

        assertThrows(co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion.class,
                () -> useCase.ejecutar());
    }

    @Test
    void deberiaRetornarTodosLosUsuariosCuandoEsColaborador() {
        UUID areaId = UUID.randomUUID();
        ContextoUsuarioAutenticado contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conRol(COLABORADOR).conAreaId(areaId).construir();

        UsuarioDTO usuario1 = crearUsuarioConArea(areaId);

        when(personaRepositorioConsulta.consultarUsuariosDTO()).thenReturn(List.of(usuario1));
        when(autorizacionServicio.obtenerContexto()).thenReturn(contexto);

        List<UsuarioDTO> resultado = useCase.ejecutar();

        assertEquals(1, resultado.size());
    }

    @Test
    void deberiaFiltrarSoloUsuariosDeSuSubareaCuandoEsAdminSubarea() {
        UUID subareaId = UUID.randomUUID();
        UUID otraSubareaId = UUID.randomUUID();
        ContextoUsuarioAutenticado contexto = new ContextoUsuarioAutenticadoTestDataBuilder()
                .conRol(ADMINISTRADOR_AREA).conAreaId(null).conSubareaId(subareaId).construir();

        UsuarioDTO usuarioEnSubarea = crearUsuarioConArea(subareaId);
        UsuarioDTO usuarioEnOtraSubarea = crearUsuarioConArea(otraSubareaId);

        when(personaRepositorioConsulta.consultarUsuariosDTO()).thenReturn(List.of(usuarioEnSubarea, usuarioEnOtraSubarea));
        when(autorizacionServicio.obtenerContexto()).thenReturn(contexto);

        List<UsuarioDTO> resultado = useCase.ejecutar();

        assertEquals(1, resultado.size());
        assertEquals(subareaId.toString(), resultado.get(0).getArea().getIdentificador());
    }

    @Test
    void deberiaConsultarUsuariosPaginadoPorTipo() {
        Page<UsuarioDTO> esperado = new PageImpl<>(List.of(crearUsuarioConArea(UUID.randomUUID())));
        when(personaRepositorioConsulta.consultarUsuariosDTOPaginado("Administrador de dirección", 0, 10, false))
                .thenReturn(esperado);

        Page<UsuarioDTO> resultado = useCase.ejecutar("Administrador de dirección", 0, 10, false);

        assertEquals(1, resultado.getContent().size());
        assertEquals(1, resultado.getTotalElements());
    }

    @Test
    void deberiaConsultarUsuariosPaginadoExcluyendoTipo() {
        Page<UsuarioDTO> esperado = new PageImpl<>(List.of(
                crearUsuarioConArea(UUID.randomUUID()),
                crearUsuarioConArea(UUID.randomUUID())));
        when(personaRepositorioConsulta.consultarUsuariosDTOPaginado("Administrador de dirección", 0, 10, true))
                .thenReturn(esperado);

        Page<UsuarioDTO> resultado = useCase.ejecutar("Administrador de dirección", 0, 10, true);

        assertEquals(2, resultado.getContent().size());
        assertEquals(2, resultado.getTotalElements());
    }
}
