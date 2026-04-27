package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Proyecto;
import co.edu.uco.sibe.dominio.puerto.comando.ProyectoRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.ProyectoRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
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
class ModificarProyectoUseCaseTest {

    @Mock
    private ProyectoRepositorioComando proyectoRepositorioComando;
    @Mock
    private ProyectoRepositorioConsulta proyectoRepositorioConsulta;

    private ModificarProyectoUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ModificarProyectoUseCase(proyectoRepositorioComando, proyectoRepositorioConsulta);
    }

    @Test
    void deberiaModificarProyectoExitosamente() {
        UUID identificador = UUID.randomUUID();
        Proyecto proyecto = Proyecto.construir(UUID.randomUUID(), "PRY001", "Nombre Proyecto modificado", "Objetivo del proyecto modificado test", new ArrayList<>());

        when(proyectoRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(Proyecto.construir());
        when(proyectoRepositorioConsulta.consultarPorNumeroProyecto(proyecto.getNumeroProyecto())).thenReturn(null);
        when(proyectoRepositorioComando.modificar(proyecto, identificador)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(proyecto, identificador);

        assertEquals(identificador, resultado);
    }

    @Test
    void deberiaLanzarExcepcionCuandoProyectoNoExiste() {
        UUID identificador = UUID.randomUUID();
        Proyecto proyecto = Proyecto.construir(UUID.randomUUID(), "PRY002", "Nombre Proyecto Noexiste", "Objetivo del proyecto no existe test", new ArrayList<>());

        when(proyectoRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class,
                () -> useCase.ejecutar(proyecto, identificador));
    }

    @Test
    void deberiaLanzarExcepcionCuandoNumeroProyectoDuplicado() {
        UUID identificador = UUID.randomUUID();
        UUID otroIdentificador = UUID.randomUUID();
        Proyecto proyecto = Proyecto.construir(UUID.randomUUID(), "PRY003", "Nombre Proyecto Duplicado", "Objetivo del proyecto duplicado test", new ArrayList<>());
        Proyecto proyectoExistente = mock(Proyecto.class);

        when(proyectoExistente.getIdentificador()).thenReturn(otroIdentificador);

        when(proyectoRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(Proyecto.construir());
        when(proyectoRepositorioConsulta.consultarPorNumeroProyecto(proyecto.getNumeroProyecto())).thenReturn(proyectoExistente);

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(proyecto, identificador));

        assertEquals(NUMERO_PROYECTO_EXISTENTE, excepcion.getMessage());
    }

    @Test
    void deberiaPermitirMismoNumeroSiEsElMismoProyecto() {
        UUID identificador = UUID.randomUUID();
        Proyecto proyecto = Proyecto.construir(UUID.randomUUID(), "PRY004", "Nombre Proyecto Mismo Num", "Objetivo del proyecto mismo numero", new ArrayList<>());
        Proyecto proyectoExistente = mock(Proyecto.class);

        when(proyectoExistente.getIdentificador()).thenReturn(identificador);

        when(proyectoRepositorioConsulta.consultarPorIdentificador(identificador)).thenReturn(Proyecto.construir());
        when(proyectoRepositorioConsulta.consultarPorNumeroProyecto(proyecto.getNumeroProyecto())).thenReturn(proyectoExistente);
        when(proyectoRepositorioComando.modificar(proyecto, identificador)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(proyecto, identificador);

        assertEquals(identificador, resultado);
    }
}
