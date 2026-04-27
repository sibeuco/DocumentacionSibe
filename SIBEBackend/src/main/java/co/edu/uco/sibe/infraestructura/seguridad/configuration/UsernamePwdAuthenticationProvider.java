package co.edu.uco.sibe.infraestructura.seguridad.configuration;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarUsuarioPorCorreoManejador;
import co.edu.uco.sibe.dominio.dto.TipoUsuarioDTO;
import co.edu.uco.sibe.dominio.puerto.servicio.EncriptarClaveServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto;
import co.edu.uco.sibe.infraestructura.adaptador.dao.PersonaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.USUARIO_O_CLAVE_INCORRECTO;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.*;
/**
 * Custom AuthenticationProvider that validates username and password credentials against the application's data source.
 *
 * <p>
 * This provider implements the Spring Security AuthenticationProvider interface. It retrieves the user from the database,
 * checks the password using an encryption service, and maps user roles to granted authorities.
 * If authentication is successful, it returns an authenticated Authentication object.
 * Otherwise, it throws an exception.
 * </p>
 *
 * <p>
 * This class depends on UserDAO, GetUserByEmailHandler, and EncryptTextService beans for its operations.
 * </p>
 */
@Component
public class UsernamePwdAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private PersonaDAO personaDAO;
    @Autowired
    private ConsultarUsuarioPorCorreoManejador consultarUsuarioPorCorreoManejador;
    @Autowired
    private EncriptarClaveServicio encriptarClaveServicio;

    /**
     * Authenticates the user by verifying their username and password against the database.
     * If successful, returns an authenticated token with the user's authorities.
     *
     * @param authentication the authentication request object containing credentials
     * @return a fully authenticated Authentication object if successful
     * @throws AuthenticationException if authentication fails
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username = authentication.getName();
        var pwd = authentication.getCredentials().toString();
        var user = usuarioDAO.findByCorreo(username);
        var userDTO = this.consultarUsuarioPorCorreoManejador.ejecutar(username);

        // Check if the user exists in the database
        if(!ValidadorObjeto.esNulo(user)) {
            // Validate the password using the encryption service
            if (this.encriptarClaveServicio.existe(pwd, user.getClave())) {
                // Build the Authentication object with authorities
                var persona = personaDAO.findByCorreo(username);
                var authenticationToken = new UsernamePasswordAuthenticationToken(username, pwd, getGrantedAuthorities(userDTO.getTipoUsuario()));
                authenticationToken.setDetails(persona.getIdentificador());

                return authenticationToken;
            } else {
                throw new AuthorizationException(USUARIO_O_CLAVE_INCORRECTO);
            }
        } else {
            throw new AuthorizationException(USUARIO_O_CLAVE_INCORRECTO);
        }
    }

    /**
     * Maps the user's role to a list of granted authorities, including CRUD privileges.
     *
     * @param authority the user's role DTO
     * @return a list of granted authorities for this user
     */
    private List<GrantedAuthority> getGrantedAuthorities(TipoUsuarioDTO authority) {
        var grantedAuthorities = new ArrayList<GrantedAuthority>();
        addCrudPrivilege(grantedAuthorities, authority);
        grantedAuthorities.add(new SimpleGrantedAuthority(authority.getCodigo()));

        return grantedAuthorities;
    }

    // Helper methods for CRUD privileges

    private boolean haveReadPrivilege(List<GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(READ_AUTHORITY));
    }

    private boolean haveWritePrivilege(List<GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(CREATE_AUTHORITY));
    }

    private boolean haveUpdatePrivilege(List<GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(UPDATE_AUTHORITY));
    }

    private boolean haveDeletePrivilege(List<GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(DELETE_AUTHORITY));
    }

    /**
     * Adds CRUD privileges to the authority list if the role allows them and they are not already present.
     *
     * @param grantedAuthorities the list of authorities to which privileges will be added
     * @param authority the user's role DTO
     */
    private void addCrudPrivilege(List<GrantedAuthority> grantedAuthorities, TipoUsuarioDTO authority) {
        if (authority.isConsultar() && !haveReadPrivilege(grantedAuthorities)) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getCodigo() + UNDERSCORE + READ_AUTHORITY));
        }
        if (authority.isCrear() && !haveWritePrivilege(grantedAuthorities)) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getCodigo() + UNDERSCORE + CREATE_AUTHORITY));
        }
        if (authority.isModificar() && !haveUpdatePrivilege(grantedAuthorities)) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getCodigo() + UNDERSCORE + UPDATE_AUTHORITY));
        }
        if (authority.isEliminar() && !haveDeletePrivilege(grantedAuthorities)) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getCodigo() + UNDERSCORE + DELETE_AUTHORITY));
        }
    }

    /**
     * Indicates whether this AuthenticationProvider supports the given Authentication object.
     * In this case, it supports UsernamePasswordAuthenticationToken.
     *
     * @param authentication the class of the authentication object
     * @return true if supported, false otherwise
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}