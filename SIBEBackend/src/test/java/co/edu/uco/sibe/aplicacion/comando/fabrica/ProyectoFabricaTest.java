package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.ProyectoComando;
import co.edu.uco.sibe.aplicacion.comando.ProyectoModificacionComando;
import co.edu.uco.sibe.dominio.modelo.Accion;
import co.edu.uco.sibe.dominio.modelo.Proyecto;
import co.edu.uco.sibe.dominio.puerto.consulta.AccionRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.ProyectoRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProyectoFabricaTest {

    @Mock
    private AccionRepositorioConsulta accionRepositorioConsulta;
    @Mock
    private ProyectoRepositorioConsulta proyectoRepositorioConsulta;

    private ProyectoFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new ProyectoFabrica(accionRepositorioConsulta, proyectoRepositorioConsulta);
    }

    @Test
    void deberiaConstruirProyectoDesdeComando() {
        UUID accionId = UUID.randomUUID();
        List<Accion> acciones = new ArrayList<>();
        when(proyectoRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);
        when(accionRepositorioConsulta.consultarTodosPorIdentificadores(any())).thenReturn(acciones);

        ProyectoComando comando = new ProyectoComando("PRY001", "Proyecto Test AB", "Objetivo del proyecto test AB", List.of(accionId.toString()));

        Proyecto resultado = fabrica.construir(comando);

        assertNotNull(resultado.getIdentificador());
        assertEquals("Proyecto Test AB", resultado.getNombre());
        assertEquals("Objetivo del proyecto test AB", resultado.getObjetivo());
    }

    @Test
    void deberiaConstruirActualizarProyecto() {
        UUID id = UUID.randomUUID();
        UUID accionId = UUID.randomUUID();
        List<Accion> acciones = new ArrayList<>();
        when(accionRepositorioConsulta.consultarTodosPorIdentificadores(any())).thenReturn(acciones);

        ProyectoModificacionComando comando = new ProyectoModificacionComando("PRYMOD01", "Proyecto Modificado", "Objetivo modificado del test", List.of(accionId.toString()));

        Proyecto resultado = fabrica.construirActualizar(comando, id);

        assertEquals(id, resultado.getIdentificador());
        assertEquals("Proyecto Modificado", resultado.getNombre());
        assertEquals("Objetivo modificado del test", resultado.getObjetivo());
    }
}
