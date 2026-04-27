package co.edu.uco.sibe.infraestructura.seguridad.filter;

import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import co.edu.uco.sibe.infraestructura.adaptador.dao.UsuarioDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.UsuarioOrganizacionDAO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.TREINTA_MILLONES;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.SIN_PERMISOS_USUARIO_NO_ENCONTRADO;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.COMMA;
import static co.edu.uco.sibe.dominio.transversal.constante.ApiEndpointConstante.LOGIN_API;

/**
 * JWTTokenGeneratorFilter creates a JWT token and attaches it to the HTTP
 * response after a successful authentication.
 *
 * <p>
 * This filter extracts user information and authorities from the Spring
 * Security context,
 * builds a signed JWT, and adds it as a response header. The token is later
 * used by the client for
 * subsequent authenticated requests.
 * </p>
 *
 * <p>
 * This filter is stateless and does not depend on any injected beans.
 * </p>
 */
@AllArgsConstructor
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
    private final UsuarioOrganizacionDAO usuarioOrganizacionDAO;
    private final UsuarioDAO usuarioDAO;

    /**
     * After successful authentication, generates a JWT token containing user info
     * and authorities,
     * signs it, and attaches it to the HTTP response header for the client to use.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @param chain    the filter chain
     * @throws IOException      if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getPrincipal();

        var usuarioEntidad = usuarioDAO.findByCorreo(authentication.getName());
        if (usuarioEntidad == null) {
            throw new AuthorizationException(SIN_PERMISOS_USUARIO_NO_ENCONTRADO);
        }
        var usuarioOrganizacion = usuarioOrganizacionDAO.findByUsuario(usuarioEntidad);

        var key = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
        var jwtBuilder = Jwts.builder().setIssuer(APP_NAME).setSubject(JWT_TOKEN)
                .claim(EMAIL_PARAMETER, authentication.getName())
                .claim(ID_PARAMETER, authentication.getDetails())
                .claim(AUTHORITIES_PARAMETER, populateAuthorities(authentication.getAuthorities()))
                .claim(ROL_PARAMETER, usuarioEntidad.getRol().getTipoUsuario().getCodigo());

        if (usuarioOrganizacion != null) {
            if (usuarioOrganizacion.getDireccion() != null) {
                jwtBuilder.claim(DIRECCION_ID_PARAMETER, usuarioOrganizacion.getDireccion().getIdentificador().toString());
            }
            if (usuarioOrganizacion.getArea() != null) {
                jwtBuilder.claim(AREA_ID_PARAMETER, usuarioOrganizacion.getArea().getIdentificador().toString());
            }
            if (usuarioOrganizacion.getSubarea() != null) {
                jwtBuilder.claim(SUBAREA_ID_PARAMETER, usuarioOrganizacion.getSubarea().getIdentificador().toString());
            }
        }

        var jwt = jwtBuilder
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + TREINTA_MILLONES))
                .signWith(key).compact();

        response.setHeader(JWT_HEADER, jwt);
        chain.doFilter(request, response);
    }

    /**
     * Only generates the JWT token on login requests.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Only apply this filter on the login API endpoint
        return !request.getServletPath().equals(LOGIN_API);
    }

    /**
     * Helper method to serialize the user's authorities into a comma-separated
     * string.
     *
     * @param collection the granted authorities of the authenticated user
     * @return a comma-separated string of authorities
     */
    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        var authoritiesSet = new HashSet<String>();

        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }

        return String.join(COMMA, authoritiesSet);
    }
}