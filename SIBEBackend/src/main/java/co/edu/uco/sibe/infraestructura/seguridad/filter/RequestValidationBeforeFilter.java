package co.edu.uco.sibe.infraestructura.seguridad.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.ERROR_DECODIFICANDO_TOKEN_AUTENTICACION_BASICA;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.TOKEN_RECIBIDO_INVALIDO;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.AUTHENTICATION_SCHEME_BASIC;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.COLON;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.TEST_CASE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * RequestValidationBeforeFilter validates the incoming HTTP request before the authentication process.
 *
 * <p>
 * Its main function is to verify the format and content of the 'Authorization' header, especially for Basic Authentication.
 * It decodes the credentials and performs custom validations (for example, rejecting certain test cases).
 * </p>
 *
 * <p>
 * This filter operates before authentication and does not require any dependency injection.
 * </p>
 */
@Getter
public class RequestValidationBeforeFilter implements Filter {
    private final Charset credentialsCharset = StandardCharsets.UTF_8;

    /**
     * Validates the 'Authorization' header for Basic Authentication:
     * <ul>
     *     <li>Decodes the base64 credentials</li>
     *     <li>Checks for the expected format (username:password)</li>
     *     <li>Performs custom validation, such as blocking certain test emails</li>
     * </ul>
     * If validation fails, it throws an exception or returns an error response.
     *
     * @param request the HTTP servlet request
     * @param response the HTTP servlet response
     * @param chain the filter chain
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var res = (HttpServletResponse) response;
        var header = req.getHeader(AUTHORIZATION);

        if (header != null) {
            header = header.trim();
            if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)) {
                // Extract and decode base64 credentials from the header
                var base64Token = header.substring(SEIS).getBytes(StandardCharsets.UTF_8);
                byte[] decoded;

                try {
                    decoded = Base64.getDecoder().decode(base64Token);
                    var token = new String(decoded, getCredentialsCharset(req));
                    var delim = token.indexOf(COLON);

                    if (delim == UNO_NEGATIVO) {
                        // Credentials are not in expected format
                        throw new BadCredentialsException(TOKEN_RECIBIDO_INVALIDO);
                    }
                    var email = token.substring(CERO, delim);
                    // Custom business validation: reject test cases
                    if (email.toLowerCase().contains(TEST_CASE)) {
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                        return;
                    }
                } catch (IllegalArgumentException e) {
                    throw new BadCredentialsException(ERROR_DECODIFICANDO_TOKEN_AUTENTICACION_BASICA);
                }
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * Returns the character set used for decoding credentials.
     * Can be overridden for custom logic.
     */
    protected Charset getCredentialsCharset(HttpServletRequest request) {
        return getCredentialsCharset();
    }
}