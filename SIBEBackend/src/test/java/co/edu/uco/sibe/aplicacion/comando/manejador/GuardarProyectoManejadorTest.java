package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.ProyectoComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.ProyectoFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.Proyecto;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarProyectoUseCase;
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
class GuardarProyectoManejadorTest {

    @Mock private ProyectoFabrica proyectoFabrica;
    @Mock private GuardarProyectoUseCase guardarProyectoUseCase;

    private GuardarProyectoManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new GuardarProyectoManejador(proyectoFabrica, guardarProyectoUseCase);
    }

    @Test
    void deberiaEjecutarGuardarProyecto() {
        ProyectoComando comando = new ProyectoComando("PRY001", "Proyecto Test", "Objetivo Test", List.of(UUID.randomUUID().toString()));
        Proyecto proyecto = mock(Proyecto.class);
        UUID idEsperado = UUID.randomUUID();

        when(proyectoFabrica.construir(comando)).thenReturn(proyecto);
        when(guardarProyectoUseCase.ejecutar(proyecto)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(comando);

        assertEquals(idEsperado, resultado.getValor());
    }
}
