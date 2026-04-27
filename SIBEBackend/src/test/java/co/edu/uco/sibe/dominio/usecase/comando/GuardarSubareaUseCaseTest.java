package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.puerto.comando.SubareaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NOMBRE_SUB_AREA_EXISTENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarSubareaUseCaseTest {

    @Mock
    private SubareaRepositorioComando subareaRepositorioComando;
    @Mock
    private SubareaRepositorioConsulta subareaRepositorioConsulta;

    private GuardarSubareaUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GuardarSubareaUseCase(subareaRepositorioComando, subareaRepositorioConsulta);
    }

    @Test
    void deberiaGuardarSubareaExitosamente() {
        UUID identificador = UUID.randomUUID();
        Subarea subarea = Subarea.construir(UUID.randomUUID(), "Deporte Formativo", new ArrayList<>());

        when(subareaRepositorioConsulta.consultarPorNombre("Deporte Formativo")).thenReturn(null);
        when(subareaRepositorioComando.guardar(subarea)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(subarea);

        assertEquals(identificador, resultado);
        verify(subareaRepositorioComando).guardar(subarea);
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoNombreExiste() {
        Subarea subarea = Subarea.construir(UUID.randomUUID(), "Deporte Formativo", new ArrayList<>());

        when(subareaRepositorioConsulta.consultarPorNombre("Deporte Formativo"))
                .thenReturn(Subarea.construir());

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(subarea));

        assertEquals(NOMBRE_SUB_AREA_EXISTENTE, excepcion.getMessage());
        verify(subareaRepositorioComando, never()).guardar(any());
    }
}
