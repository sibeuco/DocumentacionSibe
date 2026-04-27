package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.ActividadDTO;
import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarActividadesPorDireccionUseCaseTest {

    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;

    @Mock
    private DireccionRepositorioConsulta direccionRepositorioConsulta;

    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ConsultarActividadesPorDireccionUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarActividadesPorDireccionUseCase(actividadRepositorioConsulta, direccionRepositorioConsulta,
                autorizacionServicio);
    }

    @Test
    void deberiaConsultarActividadesPorDireccion() {
        UUID direccionId = UUID.randomUUID();
        Direccion direccion = Direccion.construir(direccionId, "Direccion Test", new ArrayList<>(), new ArrayList<>());
        List<ActividadDTO> esperado = List.of(new ActividadDTO());

        doNothing().when(autorizacionServicio).validarAccesoADireccion(direccionId);
        when(direccionRepositorioConsulta.consultarPorIdentificador(direccionId)).thenReturn(direccion);
        when(actividadRepositorioConsulta.consultarPorDireccion(direccion)).thenReturn(esperado);

        List<ActividadDTO> resultado = useCase.ejecutar(direccionId.toString());

        assertEquals(esperado, resultado);
    }

    @Test
    void deberiaLanzarExcepcionCuandoDireccionNoExiste() {
        UUID direccionId = UUID.randomUUID();

        doNothing().when(autorizacionServicio).validarAccesoADireccion(direccionId);
        when(direccionRepositorioConsulta.consultarPorIdentificador(direccionId)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar(direccionId.toString()));
    }
}
