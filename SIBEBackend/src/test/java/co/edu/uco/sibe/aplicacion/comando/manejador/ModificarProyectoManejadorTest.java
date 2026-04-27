package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.ProyectoModificacionComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.ProyectoFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.dominio.modelo.Proyecto;
import co.edu.uco.sibe.dominio.usecase.comando.ModificarProyectoUseCase;
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
class ModificarProyectoManejadorTest {

    @Mock private ProyectoFabrica proyectoFabrica;
    @Mock private ModificarProyectoUseCase modificarProyectoUseCase;

    private ModificarProyectoManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ModificarProyectoManejador(proyectoFabrica, modificarProyectoUseCase);
    }

    @Test
    void deberiaEjecutarModificarProyecto() {
        UUID parametro = UUID.randomUUID();
        ProyectoModificacionComando comando = new ProyectoModificacionComando("PRY001", "Proyecto Mod", "Objetivo Mod", List.of(UUID.randomUUID().toString()));
        Proyecto proyecto = mock(Proyecto.class);
        UUID idEsperado = UUID.randomUUID();

        when(proyectoFabrica.construirActualizar(comando, parametro)).thenReturn(proyecto);
        when(modificarProyectoUseCase.ejecutar(proyecto, parametro)).thenReturn(idEsperado);

        ComandoRespuesta<UUID> resultado = manejador.ejecutar(comando, parametro);

        assertEquals(idEsperado, resultado.getValor());
    }
}
