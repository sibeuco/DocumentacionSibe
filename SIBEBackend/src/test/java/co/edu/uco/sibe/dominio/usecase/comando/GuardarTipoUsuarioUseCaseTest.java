package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.puerto.comando.TipoUsuarioRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoUsuarioRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.CODIGO_TIPO_USUARIO_EXISTENTE;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NOMBRE_TIPO_USUARIO_EXISTENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarTipoUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioRepositorioComando tipoUsuarioRepositorioComando;
    @Mock
    private TipoUsuarioRepositorioConsulta tipoUsuarioRepositorioConsulta;

    private GuardarTipoUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GuardarTipoUsuarioUseCase(tipoUsuarioRepositorioComando, tipoUsuarioRepositorioConsulta);
    }

    @Test
    void deberiaGuardarTipoUsuarioExitosamente() {
        UUID identificador = UUID.randomUUID();
        TipoUsuario tipoUsuario = TipoUsuario.construir(UUID.randomUUID(), "ADM", "Administrador del Sistema", true, true, true, true);

        when(tipoUsuarioRepositorioConsulta.consultarPorCodigo("ADM")).thenReturn(null);
        when(tipoUsuarioRepositorioConsulta.consultarPorNombre("Administrador del Sistema")).thenReturn(null);
        when(tipoUsuarioRepositorioComando.guardar(tipoUsuario)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(tipoUsuario);

        assertEquals(identificador, resultado);
        verify(tipoUsuarioRepositorioComando).guardar(tipoUsuario);
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoCodigoExiste() {
        TipoUsuario tipoUsuario = TipoUsuario.construir(UUID.randomUUID(), "ADM", "Administrador del Sistema", true, true, true, true);

        when(tipoUsuarioRepositorioConsulta.consultarPorCodigo("ADM"))
                .thenReturn(TipoUsuario.construir());

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(tipoUsuario));

        assertEquals(CODIGO_TIPO_USUARIO_EXISTENTE, excepcion.getMessage());
        verify(tipoUsuarioRepositorioComando, never()).guardar(any());
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoNombreExiste() {
        TipoUsuario tipoUsuario = TipoUsuario.construir(UUID.randomUUID(), "ADM", "Administrador del Sistema", true, true, true, true);

        when(tipoUsuarioRepositorioConsulta.consultarPorCodigo("ADM")).thenReturn(null);
        when(tipoUsuarioRepositorioConsulta.consultarPorNombre("Administrador del Sistema"))
                .thenReturn(TipoUsuario.construir());

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(tipoUsuario));

        assertEquals(NOMBRE_TIPO_USUARIO_EXISTENTE, excepcion.getMessage());
        verify(tipoUsuarioRepositorioComando, never()).guardar(any());
    }
}
