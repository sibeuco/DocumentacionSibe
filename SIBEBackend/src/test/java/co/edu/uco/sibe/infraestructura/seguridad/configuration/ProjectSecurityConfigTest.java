package co.edu.uco.sibe.infraestructura.seguridad.configuration;

import co.edu.uco.sibe.infraestructura.adaptador.dao.UsuarioDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.UsuarioOrganizacionDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProjectSecurityConfigTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpSecurity http;

    @Mock
    private UsuarioOrganizacionDAO usuarioOrganizacionDAO;

    @Mock
    private UsuarioDAO usuarioDAO;

    @Test
    void deberiaConfigurarSecurityFilterChain() throws Exception {
        var config = new ProjectSecurityConfig(usuarioOrganizacionDAO, usuarioDAO);
        var result = config.securityFilterChain(http);
        assertNotNull(result);
    }
}
