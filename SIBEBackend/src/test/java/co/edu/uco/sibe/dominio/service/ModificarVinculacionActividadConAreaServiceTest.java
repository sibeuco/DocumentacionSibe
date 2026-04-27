package co.edu.uco.sibe.dominio.service;

import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.puerto.comando.AreaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.comando.DireccionRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.comando.SubareaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModificarVinculacionActividadConAreaServiceTest {

    @Mock
    private SubareaRepositorioComando subareaRepositorioComando;
    @Mock
    private AreaRepositorioComando areaRepositorioComando;
    @Mock
    private DireccionRepositorioComando direccionRepositorioComando;
    @Mock
    private SubareaRepositorioConsulta subareaRepositorioConsulta;
    @Mock
    private AreaRepositorioConsulta areaRepositorioConsulta;
    @Mock
    private DireccionRepositorioConsulta direccionRepositorioConsulta;
    @Mock
    private VincularActividadConAreaService vincularActividadConAreaService;

    private ModificarVinculacionActividadConAreaService service;

    @BeforeEach
    void setUp() {
        service = new ModificarVinculacionActividadConAreaService(
                subareaRepositorioComando, areaRepositorioComando, direccionRepositorioComando,
                subareaRepositorioConsulta, areaRepositorioConsulta, direccionRepositorioConsulta,
                vincularActividadConAreaService);
    }

    @Test
    void deberiaDesvincularDeDireccionYVincularANuevaArea() {
        Actividad actividad = mock(Actividad.class);
        UUID nuevaAreaId = UUID.randomUUID();
        UUID resultado = UUID.randomUUID();
        Direccion direccion = mock(Direccion.class);

        when(direccionRepositorioConsulta.consultarPorActividad(actividad)).thenReturn(direccion);
        when(vincularActividadConAreaService.ejecutar(actividad, nuevaAreaId, TipoArea.AREA)).thenReturn(resultado);

        assertEquals(resultado, service.ejecutar(actividad, nuevaAreaId, TipoArea.AREA));

        verify(direccionRepositorioComando).eliminarActividad(actividad, direccion);
        verify(vincularActividadConAreaService).ejecutar(actividad, nuevaAreaId, TipoArea.AREA);
    }

    @Test
    void deberiaDesvincularDeAreaYVincularANuevaArea() {
        Actividad actividad = mock(Actividad.class);
        UUID nuevaAreaId = UUID.randomUUID();
        UUID resultado = UUID.randomUUID();
        Area area = mock(Area.class);

        when(direccionRepositorioConsulta.consultarPorActividad(actividad)).thenReturn(null);
        when(areaRepositorioConsulta.consultarPorActividad(actividad)).thenReturn(area);
        when(vincularActividadConAreaService.ejecutar(actividad, nuevaAreaId, TipoArea.SUBAREA)).thenReturn(resultado);

        assertEquals(resultado, service.ejecutar(actividad, nuevaAreaId, TipoArea.SUBAREA));

        verify(areaRepositorioComando).eliminarActividad(actividad, area);
    }

    @Test
    void deberiaDesvincularDeSubareaYVincularANuevaArea() {
        Actividad actividad = mock(Actividad.class);
        UUID nuevaAreaId = UUID.randomUUID();
        UUID resultado = UUID.randomUUID();
        Subarea subarea = mock(Subarea.class);

        when(direccionRepositorioConsulta.consultarPorActividad(actividad)).thenReturn(null);
        when(areaRepositorioConsulta.consultarPorActividad(actividad)).thenReturn(null);
        when(subareaRepositorioConsulta.consultarPorActividad(actividad)).thenReturn(subarea);
        when(vincularActividadConAreaService.ejecutar(actividad, nuevaAreaId, TipoArea.DIRECCION)).thenReturn(resultado);

        assertEquals(resultado, service.ejecutar(actividad, nuevaAreaId, TipoArea.DIRECCION));

        verify(subareaRepositorioComando).eliminarActividad(actividad, subarea);
    }
}
