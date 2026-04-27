package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.ActividadDTO;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
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
class ConsultarActividadesPorSubareaUseCaseTest {

    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;

    @Mock
    private SubareaRepositorioConsulta subareaRepositorioConsulta;

    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ConsultarActividadesPorSubareaUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarActividadesPorSubareaUseCase(actividadRepositorioConsulta, subareaRepositorioConsulta,
                autorizacionServicio);
    }

    @Test
    void deberiaConsultarActividadesPorSubarea() {
        UUID subareaId = UUID.randomUUID();
        Subarea subarea = Subarea.construir(subareaId, "Subarea Test", new ArrayList<>());
        List<ActividadDTO> esperado = List.of(new ActividadDTO());

        doNothing().when(autorizacionServicio).validarAccesoASubarea(subareaId);
        when(subareaRepositorioConsulta.consultarPorIdentificador(subareaId)).thenReturn(subarea);
        when(actividadRepositorioConsulta.consultarPorSubarea(subarea)).thenReturn(esperado);

        List<ActividadDTO> resultado = useCase.ejecutar(subareaId.toString());

        assertEquals(esperado, resultado);
    }

    @Test
    void deberiaLanzarExcepcionCuandoSubareaNoExiste() {
        UUID subareaId = UUID.randomUUID();

        doNothing().when(autorizacionServicio).validarAccesoASubarea(subareaId);
        when(subareaRepositorioConsulta.consultarPorIdentificador(subareaId)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar(subareaId.toString()));
    }
}
