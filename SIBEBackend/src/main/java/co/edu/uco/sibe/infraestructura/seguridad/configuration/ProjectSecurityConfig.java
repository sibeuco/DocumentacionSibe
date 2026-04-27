package co.edu.uco.sibe.infraestructura.seguridad.configuration;

import co.edu.uco.sibe.infraestructura.adaptador.dao.UsuarioDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.UsuarioOrganizacionDAO;
import co.edu.uco.sibe.infraestructura.seguridad.filter.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.Collections;
import java.util.List;
import static co.edu.uco.sibe.dominio.transversal.constante.ApiEndpointConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.TRES_MIL_SEICIENTOS_LONG;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.JWT_HEADER;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.LOCAL_FRONT_URL;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.*;

/**
 * ProjectSecurityConfig is the central security configuration class for the
 * application.
 *
 * <p>
 * It defines the global security policies, including session management
 * (stateless), CORS policies,
 * CSRF protection, endpoint authorization rules, and the arrangement of custom
 * security filters.
 * </p>
 *
 * <p>
 * This class depends on all custom filters and the custom
 * AuthenticationProvider.
 * It provides a SecurityFilterChain bean that Spring Security uses to secure
 * HTTP requests.
 * </p>
 */
@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@AllArgsConstructor
public class ProjectSecurityConfig {
    private final UsuarioOrganizacionDAO usuarioOrganizacionDAO;
    private final UsuarioDAO usuarioDAO;

    /**
     * Configures the application's SecurityFilterChain, which defines all the
     * security settings and filter order.
     *
     * <ul>
     * <li>Session management is set to stateless for RESTful APIs.</li>
     * <li>CORS is configured to allow requests from the frontend origin, with all
     * methods and headers allowed.</li>
     * <li>CSRF protection is disabled for stateless APIs.</li>
     * <li>Custom filters are added in a specific order to the security filter
     * chain.</li>
     * <li>Authorization rules specify which endpoints require authentication and
     * which are public.</li>
     * <li>HTTP Basic authentication is enabled for initial login.</li>
     * </ul>
     *
     * <p>
     * The custom filters are ordered as follows:
     * <ol>
     * <li>ExceptionFilter: Handles exceptions for all subsequent filters.</li>
     * <li>RequestValidationBeforeFilter: Validates request headers before
     * authentication.</li>
     * <li>AuthoritiesLoggingAtFilter: Logs authentication attempts.</li>
     * <li>JWTTokenValidatorFilter: Validates JWTs on protected requests.</li>
     * <li>BasicAuthenticationFilter (built-in): Handles username/password
     * login.</li>
     * <li>AuthoritiesLoggingAfterFilter: Logs user roles after authentication.</li>
     * <li>JWTTokenGeneratorFilter: Generates and attaches JWT on successful
     * login.</li>
     * </ol>
     * </p>
     *
     * @param http the HttpSecurity object to configure
     * @return the fully built SecurityFilterChain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configure CORS to allow the frontend origin and expose the JWT header
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of(LOCAL_FRONT_URL));
                    config.setAllowedMethods(Collections.singletonList(ASTERISK));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList(ASTERISK));
                    config.setExposedHeaders(List.of(JWT_HEADER));
                    config.setMaxAge(TRES_MIL_SEICIENTOS_LONG);

                    return config;
                }))
                // Disable CSRF for stateless APIs
                .csrf(AbstractHttpConfigurer::disable)
                // Add custom filters in the correct order relative to BasicAuthenticationFilter
                .addFilterBefore(new ExceptionFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(usuarioOrganizacionDAO, usuarioDAO),
                        BasicAuthenticationFilter.class)
                .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
                // Define endpoint authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, USER_API + SOLICITAR_CODIGO_PATH).permitAll()
                        .requestMatchers(HttpMethod.POST, USER_API + VALIDAR_CODIGO_PATH).permitAll()
                        .requestMatchers(HttpMethod.PUT, USER_API + RECUPERAR_CLAVE_PATH).permitAll()
                        .requestMatchers(LOGIN_API).authenticated()
                        .anyRequest().authenticated())
                // Enable HTTP Basic authentication for user login
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}