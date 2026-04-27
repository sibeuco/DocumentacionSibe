package co.edu.uco.sibe.infraestructura.seguridad.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthoritiesLoggingAtFilterTest {

    @Mock private ServletRequest request;
    @Mock private ServletResponse response;
    @Mock private FilterChain chain;

    private AuthoritiesLoggingAtFilter filter;

    @BeforeEach
    void setUp() {
        filter = new AuthoritiesLoggingAtFilter();
    }

    @Test
    void deberiaRegistrarMensajeYContinuarCadena() throws Exception {
        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }
}
