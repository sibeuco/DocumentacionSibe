package co.edu.uco.sibe.infraestructura.seguridad.filter;

import jakarta.servlet.*;
import org.springframework.security.core.context.SecurityContextHolder;
import java.io.IOException;
import java.util.logging.Logger;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeDeConfirmacionDeInicioSesion;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

/**
 * This filter logs the roles (authorities) of an authenticated user after authentication has taken place.
 *
 * <p>
 * Its main purpose is to provide audit logs or debugging information about which user has been authenticated and
 * the roles assigned to them. This is especially useful for monitoring authorization issues or unexpected role assignments.
 * </p>
 *
 * <p>
 * This filter does not depend on injected beans or services. It only uses constants and the Spring Security context.
 * </p>
 */
public class AuthoritiesLoggingAfterFilter implements Filter {
    private final Logger log = Logger.getLogger(AuthoritiesLoggingAfterFilter.class.getName());

    /**
     * After authentication, logs the authenticated user's name and their authorities (roles),
     * then continues the filter chain.
     *
     * @param request the incoming ServletRequest
     * @param response the outgoing ServletResponse
     * @param chain the filter chain
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Retrieve the current Authentication object from the SecurityContext
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        // If a user is authenticated, log their username and authorities
        if (!esNulo(authentication)) {
            log.info(obtenerMensajeDeConfirmacionDeInicioSesion(authentication.getName(), authentication.getAuthorities().toString()));
        }

        chain.doFilter(request, response);
    }
}