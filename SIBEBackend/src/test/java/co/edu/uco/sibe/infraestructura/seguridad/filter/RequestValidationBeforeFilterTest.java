package co.edu.uco.sibe.infraestructura.seguridad.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ExtendWith(MockitoExtension.class)
class RequestValidationBeforeFilterTest {

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private FilterChain filterChain;

    private RequestValidationBeforeFilter filtro;

    @BeforeEach
    void setUp() {
        filtro = new RequestValidationBeforeFilter();
    }

    @Test
    void deberiaContinuarCadenaFiltroSinHeaderAutorizacion() throws Exception {
        when(request.getHeader(AUTHORIZATION)).thenReturn(null);

        filtro.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void deberiaContinuarCadenaFiltroConHeaderNoBasic() throws Exception {
        when(request.getHeader(AUTHORIZATION)).thenReturn("Bearer some-token");

        filtro.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void deberiaContinuarConCredencialesBasicValidas() throws Exception {
        String credentials = "user@example.com:password123";
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        when(request.getHeader(AUTHORIZATION)).thenReturn("Basic " + encoded);

        filtro.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void deberiaRechazarCorreoConTestCase() throws Exception {
        String credentials = "test@test.com:password123";
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        when(request.getHeader(AUTHORIZATION)).thenReturn("Basic " + encoded);

        filtro.doFilter(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void deberiaLanzarExcepcionConTokenSinSeparadorColon() throws Exception {
        String invalidCredentials = "nokcolonhere";
        String encoded = Base64.getEncoder().encodeToString(invalidCredentials.getBytes(StandardCharsets.UTF_8));
        when(request.getHeader(AUTHORIZATION)).thenReturn("Basic " + encoded);

        assertThrows(BadCredentialsException.class, () ->
                filtro.doFilter(request, response, filterChain));
    }

    @Test
    void deberiaLanzarExcepcionConBase64Invalido() throws Exception {
        when(request.getHeader(AUTHORIZATION)).thenReturn("Basic !!!invalid-base64!!!");

        assertThrows(BadCredentialsException.class, () ->
                filtro.doFilter(request, response, filterChain));
    }

    @Test
    void deberiaRetornarCredentialsCharsetUTF8() {
        assertEquals(StandardCharsets.UTF_8, filtro.getCredentialsCharset());
    }
}
