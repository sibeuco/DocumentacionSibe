package co.edu.uco.sibe.infraestructura.seguridad.filter;

import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.*;

/**
 * ExceptionFilter is responsible for catching and processing exceptions that may occur during the security filter chain execution.
 *
 * <p>
 * Its primary goal is to ensure that any authentication or authorization errors are translated into meaningful HTTP responses,
 * with appropriate status codes and messages. This provides a better user experience and helps clients handle errors.
 * </p>
 *
 * <p>
 * This filter does not depend on any beans or services, making it reusable in any security configuration.
 * </p>
 */
@NoArgsConstructor
public class ExceptionFilter extends OncePerRequestFilter {
    /**
     * Catches exceptions thrown by subsequent filters and maps them to HTTP status codes and messages.
     *
     * <ul>
     *   <li>BadCredentialsException: returns HTTP 400 (Bad Request)</li>
     *   <li>AuthorizationException: returns HTTP 401 (Unauthorized)</li>
     *   <li>Other exceptions: returns HTTP 500 (Internal Server Error)</li>
     * </ul>
     *
     * @param request the HTTP servlet request
     * @param response the HTTP servlet response
     * @param filterChain the filter chain
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            // Default to HTTP 500 (Internal Server Error)
            var status = QUINIENTOS;

            // Map known exceptions to specific HTTP status codes
            if (ex instanceof BadCredentialsException) {
                status = CUATROCIENTOS;
            } else if (ex instanceof AuthorizationException) {
                status = CUATROCIENTOS_TRES;
            }

            response.setStatus(status);
            response.setContentType("application/json");
            response.getWriter().write("{\"nombreExcepcion\":\"" + ex.getClass().getSimpleName()
                    + "\",\"mensaje\":\"" + ex.getLocalizedMessage().replace("\"", "\\\"") + "\"}");
        }
    }
}