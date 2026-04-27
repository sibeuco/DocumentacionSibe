package co.edu.uco.sibe.infraestructura.seguridad.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthoritiesLoggingAfterFilterTest {

    @Mock private ServletRequest request;
    @Mock private ServletResponse response;
    @Mock private FilterChain chain;

    private AuthoritiesLoggingAfterFilter filter;

    @BeforeEach
    void setUp() {
        filter = new AuthoritiesLoggingAfterFilter();
        SecurityContextHolder.clearContext();
    }

    @Test
    void deberiaRegistrarAutoridadesCuandoHayAutenticacion() throws Exception {
        var auth = new UsernamePasswordAuthenticationToken("user", "pass",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        SecurityContextHolder.getContext().setAuthentication(auth);

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }

    @Test
    void deberiaContinuarSinAutenticacion() throws Exception {
        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }
}
