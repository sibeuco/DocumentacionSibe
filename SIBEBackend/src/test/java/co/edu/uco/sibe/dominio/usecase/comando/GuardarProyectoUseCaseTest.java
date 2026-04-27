package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Proyecto;
import co.edu.uco.sibe.dominio.puerto.comando.ProyectoRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.ProyectoRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NUMERO_PROYECTO_EXISTENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarProyectoUseCaseTest {

    @Mock
    private ProyectoRepositorioComando proyectoRepositorioComando;
    @Mock
    private ProyectoRepositorioConsulta proyectoRepositorioConsulta;

    private GuardarProyectoUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GuardarProyectoUseCase(proyectoRepositorioComando, proyectoRepositorioConsulta);
    }

    @Test
    void deberiaGuardarProyectoExitosamente() {
        UUID identificador = UUID.randomUUID();
        Proyecto proyecto = Proyecto.construir(UUID.randomUUID(), "PRY001", "Nombre del Proyecto Test", "Objetivo del proyecto de prueba test", new ArrayList<>());

        when(proyectoRepositorioConsulta.consultarPorNumeroProyecto(proyecto.getNumeroProyecto())).thenReturn(null);
        when(proyectoRepositorioComando.guardar(proyecto)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(proyecto);

        assertEquals(identificador, resultado);
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoNumeroProyectoExiste() {
        Proyecto proyecto = Proyecto.construir(UUID.randomUUID(), "PRY002", "Nombre Proyecto Duplicado", "Objetivo del proyecto duplicado test", new ArrayList<>());

        when(proyectoRepositorioConsulta.consultarPorNumeroProyecto(proyecto.getNumeroProyecto()))
                .thenReturn(Proyecto.construir());

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(proyecto));

        assertEquals(NUMERO_PROYECTO_EXISTENTE, excepcion.getMessage());
        verify(proyectoRepositorioComando, never()).guardar(any());
    }
}
