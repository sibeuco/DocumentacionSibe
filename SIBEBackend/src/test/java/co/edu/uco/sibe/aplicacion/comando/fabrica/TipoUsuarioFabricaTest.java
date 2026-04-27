package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.TipoUsuarioComando;
import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoUsuarioRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioFabricaTest {

    @Mock
    private TipoUsuarioRepositorioConsulta tipoUsuarioRepositorioConsulta;

    private TipoUsuarioFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new TipoUsuarioFabrica(tipoUsuarioRepositorioConsulta);
    }

    @Test
    void deberiaConstruirTipoUsuarioDesdeComando() {
        when(tipoUsuarioRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);
        TipoUsuarioComando comando = new TipoUsuarioComando("ADM", "Administrador del Sistema", true, true, true, true);

        TipoUsuario resultado = fabrica.construir(comando);

        assertNotNull(resultado.getIdentificador());
        assertEquals("ADM", resultado.getCodigo());
        assertEquals("Administrador del Sistema", resultado.getNombre());
        assertTrue(resultado.isCrear());
        assertTrue(resultado.isModificar());
        assertTrue(resultado.isEliminar());
        assertTrue(resultado.isConsultar());
    }
}
