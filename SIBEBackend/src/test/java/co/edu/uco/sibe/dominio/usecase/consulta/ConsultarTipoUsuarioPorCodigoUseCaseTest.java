package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoUsuarioRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarTipoUsuarioPorCodigoUseCaseTest {

    @Mock
    private TipoUsuarioRepositorioConsulta tipoUsuarioRepositorioConsulta;

    private ConsultarTipoUsuarioPorCodigoUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarTipoUsuarioPorCodigoUseCase(tipoUsuarioRepositorioConsulta);
    }

    @Test
    void deberiaRetornarTipoUsuarioPorCodigoExitosamente() {
        TipoUsuario tipoUsuario = TipoUsuario.construir();
        when(tipoUsuarioRepositorioConsulta.consultarPorCodigo("ADM")).thenReturn(tipoUsuario);

        TipoUsuario resultado = useCase.ejecutar("ADM");

        assertEquals(tipoUsuario, resultado);
    }

    @Test
    void deberiaLanzarExcepcionCuandoTipoUsuarioNoExistePorCodigo() {
        when(tipoUsuarioRepositorioConsulta.consultarPorCodigo("XXX")).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar("XXX"));
    }
}
