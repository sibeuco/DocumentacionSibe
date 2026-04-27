package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.IndicadorComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.IndicadorFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.Indicador;
import co.edu.uco.sibe.dominio.usecase.comando.ModificarIndicadorUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModificarIndicadorManejadorTest {

    @Mock private IndicadorFabrica indicadorFabrica;
    @Mock private ModificarIndicadorUseCase modificarIndicadorUseCase;

    private ModificarIndicadorManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ModificarIndicadorManejador(indicadorFabrica, modificarIndicadorUseCase);
    }

    @Test
    void deberiaEjecutarModificarIndicador() {
        UUID parametro = UUID.randomUUID();
        IndicadorComando comando = new IndicadorComando("Indicador Mod", UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), List.of(UUID.randomUUID().toString()));
        Indicador indicador = mock(Indicador.class);
        UUID idEsperado = UUID.randomUUID();

        when(indicadorFabrica.construirActualizar(comando, parametro)).thenReturn(indicador);
        when(modificarIndicadorUseCase.ejecutar(indicador, parametro)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(comando, parametro);

        assertEquals(idEsperado, resultado.getValor());
    }
}
