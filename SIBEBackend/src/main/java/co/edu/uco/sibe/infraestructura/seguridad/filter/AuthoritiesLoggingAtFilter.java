package co.edu.uco.sibe.infraestructura.seguridad.filter;

import jakarta.servlet.*;
import java.io.IOException;
import java.util.logging.Logger;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.VALIDANDO_AUTENTICACION;

/**
 * This filter logs an informational message indicating that the authentication process is being initiated.
 *
 * <p>
 * It is placed in the filter chain at a position such that it executes right before the core authentication logic.
 * This can help with diagnostics, monitoring, or auditing, as it records every attempt to start authentication.
 * </p>
 *
 * <p>
 * This filter does not depend on any other beans or services. It only requires access to application constants.
 * </p>
 */
public class AuthoritiesLoggingAtFilter implements Filter {
    private final Logger log = Logger.getLogger(AuthoritiesLoggingAtFilter.class.getName());

    /**
     * Logs a message before the authentication process begins, then continues the filter chain.
     *
     * @param request the incoming ServletRequest
     * @param response the outgoing ServletResponse
     * @param chain the filter chain
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Log that authentication is about to be validated.
        log.info(VALIDANDO_AUTENTICACION);

        chain.doFilter(request, response);
    }
}