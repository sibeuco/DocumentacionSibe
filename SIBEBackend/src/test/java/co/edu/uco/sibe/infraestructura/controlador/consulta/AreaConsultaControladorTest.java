package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarAreaDetalladaManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarAreaPorNombreDTOManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarAreasManejador;
import co.edu.uco.sibe.dominio.dto.AreaDTO;
import co.edu.uco.sibe.dominio.dto.AreaDetalladaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AreaConsultaControladorTest {

    @Mock private ConsultarAreasManejador consultarAreasManejador;
    @Mock private ConsultarAreaDetalladaManejador consultarAreaDetalladaManejador;
    @Mock private ConsultarAreaPorNombreDTOManejador consultarAreaPorNombreDTOManejador;

    private AreaConsultaControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new AreaConsultaControlador(consultarAreasManejador, consultarAreaDetalladaManejador, consultarAreaPorNombreDTOManejador);
    }

    @Test
    void deberiaConsultarDetalle() {
        String identificador = "area-1";
        AreaDetalladaDTO dto = new AreaDetalladaDTO();
        when(consultarAreaDetalladaManejador.ejecutar(identificador)).thenReturn(dto);

        AreaDetalladaDTO resultado = controlador.consultarDetalle(identificador);

        assertNotNull(resultado);
        verify(consultarAreaDetalladaManejador).ejecutar(identificador);
    }

    @Test
    void deberiaConsultarTodos() {
        List<AreaDTO> areas = List.of(new AreaDTO());
        when(consultarAreasManejador.ejecutar()).thenReturn(areas);

        List<AreaDTO> resultado = controlador.consultarTodos();

        assertEquals(1, resultado.size());
        verify(consultarAreasManejador).ejecutar();
    }

    @Test
    void deberiaConsultarPorNombre() {
        String nombre = "Area Test";
        AreaDTO dto = new AreaDTO();
        when(consultarAreaPorNombreDTOManejador.ejecutar(nombre)).thenReturn(dto);

        AreaDTO resultado = controlador.consultarPorNombre(nombre);

        assertNotNull(resultado);
        verify(consultarAreaPorNombreDTOManejador).ejecutar(nombre);
    }
}
