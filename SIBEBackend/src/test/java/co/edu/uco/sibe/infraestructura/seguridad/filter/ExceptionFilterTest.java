package co.edu.uco.sibe.infraestructura.seguridad.filter;

import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExceptionFilterTest {

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private FilterChain filterChain;

    private ExceptionFilter exceptionFilter;

    @BeforeEach
    void setUp() {
        exceptionFilter = new ExceptionFilter();
    }

    @Test
    void deberiaContinuarCadenaFiltrosSinExcepcion() throws Exception {
        exceptionFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(response, never()).setStatus(anyInt());
    }

    @Test
    void deberiaRetornar400ParaBadCredentialsException() throws Exception {
        StringWriter sw = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(sw));
        doThrow(new BadCredentialsException("Credenciales inválidas"))
                .when(filterChain).doFilter(request, response);

        exceptionFilter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(400);
    }

    @Test
    void deberiaRetornar403ParaAuthorizationException() throws Exception {
        StringWriter sw = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(sw));
        doThrow(new AuthorizationException("No autorizado"))
                .when(filterChain).doFilter(request, response);

        exceptionFilter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(403);
    }

    @Test
    void deberiaRetornar500ParaExcepcionGeneral() throws Exception {
        StringWriter sw = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(sw));
        doThrow(new RuntimeException("Error inesperado"))
                .when(filterChain).doFilter(request, response);

        exceptionFilter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(500);
    }
}
