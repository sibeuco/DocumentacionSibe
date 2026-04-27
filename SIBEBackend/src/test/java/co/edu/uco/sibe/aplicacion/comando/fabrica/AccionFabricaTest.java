package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.AccionComando;
import co.edu.uco.sibe.dominio.modelo.Accion;
import co.edu.uco.sibe.dominio.puerto.consulta.AccionRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccionFabricaTest {

    @Mock
    private AccionRepositorioConsulta accionRepositorioConsulta;

    private AccionFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new AccionFabrica(accionRepositorioConsulta);
    }

    @Test
    void deberiaConstruirAccionDesdeComando() {
        when(accionRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);
        AccionComando comando = new AccionComando("Detalle de la accion test", "Objetivo de la accion test");

        Accion resultado = fabrica.construir(comando);

        assertNotNull(resultado.getIdentificador());
        assertEquals("Detalle de la accion test", resultado.getDetalle());
        assertEquals("Objetivo de la accion test", resultado.getObjetivo());
    }

    @Test
    void deberiaConstruirActualizarAccion() {
        UUID id = UUID.randomUUID();
        AccionComando comando = new AccionComando("Detalle actualizado test", "Objetivo actualizado test");

        Accion resultado = fabrica.construirActualizar(comando, id);

        assertEquals(id, resultado.getIdentificador());
        assertEquals("Detalle actualizado test", resultado.getDetalle());
        assertEquals("Objetivo actualizado test", resultado.getObjetivo());
    }
}
