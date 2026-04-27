package co.edu.uco.sibe.dominio.service;

import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.puerto.comando.AreaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.comando.DireccionRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.comando.SubareaRepositorioComando;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VincularActividadConAreaServiceTest {

    @Mock
    private SubareaRepositorioComando subareaRepositorioComando;
    @Mock
    private AreaRepositorioComando areaRepositorioComando;
    @Mock
    private DireccionRepositorioComando direccionRepositorioComando;

    private VincularActividadConAreaService service;

    @BeforeEach
    void setUp() {
        service = new VincularActividadConAreaService(subareaRepositorioComando, areaRepositorioComando,
                direccionRepositorioComando);
    }

    @Test
    void deberiaVincularActividadConDireccion() {
        Actividad actividad = mock(Actividad.class);
        UUID area = UUID.randomUUID();
        UUID resultado = UUID.randomUUID();

        when(direccionRepositorioComando.guardarActividad(actividad, area)).thenReturn(resultado);

        assertEquals(resultado, service.ejecutar(actividad, area, TipoArea.DIRECCION));
        verify(direccionRepositorioComando).guardarActividad(actividad, area);
    }

    @Test
    void deberiaVincularActividadConArea() {
        Actividad actividad = mock(Actividad.class);
        UUID area = UUID.randomUUID();
        UUID resultado = UUID.randomUUID();

        when(areaRepositorioComando.guardarActividad(actividad, area)).thenReturn(resultado);

        assertEquals(resultado, service.ejecutar(actividad, area, TipoArea.AREA));
        verify(areaRepositorioComando).guardarActividad(actividad, area);
    }

    @Test
    void deberiaVincularActividadConSubarea() {
        Actividad actividad = mock(Actividad.class);
        UUID area = UUID.randomUUID();
        UUID resultado = UUID.randomUUID();

        when(subareaRepositorioComando.guardarActividad(actividad, area)).thenReturn(resultado);

        assertEquals(resultado, service.ejecutar(actividad, area, TipoArea.SUBAREA));
        verify(subareaRepositorioComando).guardarActividad(actividad, area);
    }
}
