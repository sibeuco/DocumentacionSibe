package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ContarUsuariosPorOrganizacionManejador;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrganizacionConsultaControladorTest {

    @Mock private ContarUsuariosPorOrganizacionManejador contarUsuariosPorOrganizacionManejador;

    private OrganizacionConsultaControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new OrganizacionConsultaControlador(contarUsuariosPorOrganizacionManejador);
    }

    @Test
    void deberiaContarUsuarios() {
        String identificador = "org-1";
        when(contarUsuariosPorOrganizacionManejador.ejecutar(identificador)).thenReturn(new ComandoRespuesta<>(10L));

        ComandoRespuesta<Long> resultado = controlador.contarUsuarios(identificador);

        assertEquals(10L, resultado.getValor());
        verify(contarUsuariosPorOrganizacionManejador).ejecutar(identificador);
    }
}
