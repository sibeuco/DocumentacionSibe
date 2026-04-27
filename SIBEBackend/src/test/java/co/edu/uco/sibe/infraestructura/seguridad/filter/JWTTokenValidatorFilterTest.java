package co.edu.uco.sibe.infraestructura.seguridad.filter;

import co.edu.uco.sibe.dominio.dto.ContextoUsuarioAutenticado;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JWTTokenValidatorFilterTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    private JWTTokenValidatorFilter filtro;
    private UUID usuarioId;
    private UUID direccionId;
    private UUID areaId;
    private UUID subareaId;

    @BeforeEach
    void setUp() {
        filtro = new JWTTokenValidatorFilter();
        usuarioId = UUID.randomUUID();
        direccionId = UUID.randomUUID();
        areaId = UUID.randomUUID();
        subareaId = UUID.randomUUID();
        SecurityContextHolder.clearContext();
    }

    @Test
    void deberiaExtraerContextoUsuarioAutenticadoDelJWT() throws Exception {
        var jwt = generarJWTConContexto(ADMINISTRADOR_AREA);
        when(request.getHeader(JWT_HEADER)).thenReturn(jwt);

        filtro.doFilterInternal(request, response, filterChain);

        var auth = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(auth);
        assertInstanceOf(ContextoUsuarioAutenticado.class, auth.getDetails());

        var contexto = (ContextoUsuarioAutenticado) auth.getDetails();
        assertEquals(usuarioId, contexto.getIdentificador());
        assertEquals("test@test.com", contexto.getCorreo());
        assertEquals(ADMINISTRADOR_AREA, contexto.getRol());
        assertEquals(direccionId, contexto.getDireccionId());
        assertEquals(areaId, contexto.getAreaId());
        assertEquals(subareaId, contexto.getSubareaId());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void deberiaLanzarExcepcionConTokenInvalido() {
        when(request.getHeader(JWT_HEADER)).thenReturn("token-invalido");

        assertThrows(BadCredentialsException.class, () -> filtro.doFilterInternal(request, response, filterChain));
    }

    @Test
    void deberiaContinuarSinTokenEnElHeader() throws Exception {
        when(request.getHeader(JWT_HEADER)).thenReturn(null);

        filtro.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void deberiaNoFiltrarEnEndpointDeLogin() {
        when(request.getServletPath()).thenReturn("/login");
        assertTrue(filtro.shouldNotFilter(request));
    }

    @Test
    void deberiaFiltrarEnEndpointsProtegidos() {
        when(request.getServletPath()).thenReturn("/api/usuarios");
        assertFalse(filtro.shouldNotFilter(request));
    }

    private String generarJWTConContexto(String rol) {
        var key = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setIssuer(APP_NAME)
                .setSubject(JWT_TOKEN)
                .claim(EMAIL_PARAMETER, "test@test.com")
                .claim(ID_PARAMETER, usuarioId.toString())
                .claim(USERNAME, "test@test.com")
                .claim(AUTHORITIES_PARAMETER, "ADMINISTRADOR_AREA_READ")
                .claim(ROL_PARAMETER, rol)
                .claim(DIRECCION_ID_PARAMETER, direccionId.toString())
                .claim(AREA_ID_PARAMETER, areaId.toString())
                .claim(SUBAREA_ID_PARAMETER, subareaId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 30000000))
                .signWith(key)
                .compact();
    }
}
