package co.edu.uco.sibe.infraestructura.seguridad.filter;

import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import co.edu.uco.sibe.infraestructura.adaptador.dao.UsuarioDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.UsuarioOrganizacionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JWTTokenGeneratorFilterTest {

    @Mock
    private UsuarioOrganizacionDAO usuarioOrganizacionDAO;
    @Mock
    private UsuarioDAO usuarioDAO;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    private JWTTokenGeneratorFilter filtro;
    private UUID usuarioId;
    private UUID direccionId;
    private UUID areaId;
    private UUID subareaId;

    @BeforeEach
    void setUp() {
        filtro = new JWTTokenGeneratorFilter(usuarioOrganizacionDAO, usuarioDAO);
        usuarioId = UUID.randomUUID();
        direccionId = UUID.randomUUID();
        areaId = UUID.randomUUID();
        subareaId = UUID.randomUUID();
        SecurityContextHolder.clearContext();
    }

    @Test
    void deberiaGenerarJWTConClaimsOrganizacionalesCuandoUsuarioTieneContexto() throws Exception {
        var auth = new UsernamePasswordAuthenticationToken("test@test.com", "pwd",
                List.of(new SimpleGrantedAuthority(ADMIN_GET_AUTHORITY)));
        auth.setDetails(usuarioId);
        SecurityContextHolder.getContext().setAuthentication(auth);

        var usuarioEntidad = crearUsuarioEntidad(ADMINISTRADOR_DIRECCION);
        var orgEntidad = crearUsuarioOrganizacionEntidad(usuarioEntidad);

        when(usuarioDAO.findByCorreo("test@test.com")).thenReturn(usuarioEntidad);
        when(usuarioOrganizacionDAO.findByUsuario(usuarioEntidad)).thenReturn(orgEntidad);

        var headerCaptor = ArgumentCaptor.forClass(String.class);
        filtro.doFilterInternal(request, response, filterChain);

        verify(response).setHeader(eq(JWT_HEADER), headerCaptor.capture());
        var jwtToken = headerCaptor.getValue();
        assertNotNull(jwtToken);

        var key = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();

        assertEquals(direccionId.toString(), claims.get(DIRECCION_ID_PARAMETER));
        assertEquals(areaId.toString(), claims.get(AREA_ID_PARAMETER));
        assertEquals(subareaId.toString(), claims.get(SUBAREA_ID_PARAMETER));
        assertEquals(ADMINISTRADOR_DIRECCION, claims.get(ROL_PARAMETER));
        assertEquals("test@test.com", claims.get(EMAIL_PARAMETER));
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void deberiaGenerarJWTSinClaimsOrganizacionalesCuandoUsuarioNoTieneContexto() throws Exception {
        var auth = new UsernamePasswordAuthenticationToken("test@test.com", "pwd",
                List.of(new SimpleGrantedAuthority(ADMIN_GET_AUTHORITY)));
        auth.setDetails(usuarioId);
        SecurityContextHolder.getContext().setAuthentication(auth);

        var usuarioEntidad = crearUsuarioEntidad(ADMINISTRADOR_DIRECCION);
        when(usuarioDAO.findByCorreo("test@test.com")).thenReturn(usuarioEntidad);
        when(usuarioOrganizacionDAO.findByUsuario(usuarioEntidad)).thenReturn(null);

        var headerCaptor = ArgumentCaptor.forClass(String.class);
        filtro.doFilterInternal(request, response, filterChain);

        verify(response).setHeader(eq(JWT_HEADER), headerCaptor.capture());
        var jwtToken = headerCaptor.getValue();
        assertNotNull(jwtToken);

        var key = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();

        assertNull(claims.get(DIRECCION_ID_PARAMETER));
        assertNull(claims.get(AREA_ID_PARAMETER));
        assertNull(claims.get(SUBAREA_ID_PARAMETER));
        assertEquals(ADMINISTRADOR_DIRECCION, claims.get(ROL_PARAMETER));
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void deberiaLanzarExcepcionCuandoUsuarioNoExiste() {
        var auth = new UsernamePasswordAuthenticationToken("test@test.com", "pwd",
                List.of(new SimpleGrantedAuthority(ADMIN_GET_AUTHORITY)));
        auth.setDetails(usuarioId);
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(usuarioDAO.findByCorreo("test@test.com")).thenReturn(null);

        assertThrows(AuthorizationException.class, () -> filtro.doFilterInternal(request, response, filterChain));
    }

    @Test
    void deberiaNoFiltrarEnEndpointsProtegidos() {
        when(request.getServletPath()).thenReturn("/api/usuarios");
        assertTrue(filtro.shouldNotFilter(request));
    }

    @Test
    void deberiaFiltrarEnEndpointDeLogin() {
        when(request.getServletPath()).thenReturn("/login");
        assertFalse(filtro.shouldNotFilter(request));
    }

    private UsuarioEntidad crearUsuarioEntidad(String codigoRol) {
        var tipoUsuario = new TipoUsuarioEntidad();
        tipoUsuario.setIdentificador(UUID.randomUUID());
        tipoUsuario.setCodigo(codigoRol);
        tipoUsuario.setNombre(codigoRol);

        var usuarioTipoUsuario = new UsuarioTipoUsuarioEntidad();
        usuarioTipoUsuario.setIdentificador(UUID.randomUUID());
        usuarioTipoUsuario.setTipoUsuario(tipoUsuario);

        var usuario = new UsuarioEntidad();
        usuario.setIdentificador(usuarioId);
        usuario.setCorreo("test@test.com");
        usuario.setClave("encrypted");
        usuario.setEstaActivo(true);
        usuario.setRol(usuarioTipoUsuario);
        return usuario;
    }

    private UsuarioOrganizacionEntidad crearUsuarioOrganizacionEntidad(UsuarioEntidad usuario) {
        var direccion = new DireccionEntidad();
        direccion.setIdentificador(direccionId);

        var area = new AreaEntidad();
        area.setIdentificador(areaId);

        var subarea = new SubareaEntidad();
        subarea.setIdentificador(subareaId);

        var org = new UsuarioOrganizacionEntidad();
        org.setIdentificador(UUID.randomUUID());
        org.setUsuario(usuario);
        org.setDireccion(direccion);
        org.setArea(area);
        org.setSubarea(subarea);
        return org;
    }
}
