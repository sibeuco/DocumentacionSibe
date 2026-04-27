package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarDireccionDetalladaManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarDireccionPorNombreDTOManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarDireccionesManejador;
import co.edu.uco.sibe.dominio.dto.DireccionDTO;
import co.edu.uco.sibe.dominio.dto.DireccionDetalladaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DireccionConsultaControladorTest {

    @Mock private ConsultarDireccionesManejador consultarDireccionesManejador;
    @Mock private ConsultarDireccionDetalladaManejador consultarDireccionDetalladaManejador;
    @Mock private ConsultarDireccionPorNombreDTOManejador consultarDireccionPorNombreDTOManejador;

    private DireccionConsultaControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new DireccionConsultaControlador(consultarDireccionesManejador, consultarDireccionDetalladaManejador, consultarDireccionPorNombreDTOManejador);
    }

    @Test
    void deberiaConsultarDetalle() {
        String identificador = "dir-1";
        DireccionDetalladaDTO dto = new DireccionDetalladaDTO();
        when(consultarDireccionDetalladaManejador.ejecutar(identificador)).thenReturn(dto);

        DireccionDetalladaDTO resultado = controlador.consultarDetalle(identificador);

        assertNotNull(resultado);
        verify(consultarDireccionDetalladaManejador).ejecutar(identificador);
    }

    @Test
    void deberiaConsultarTodos() {
        List<DireccionDTO> direcciones = List.of(new DireccionDTO());
        when(consultarDireccionesManejador.ejecutar()).thenReturn(direcciones);

        List<DireccionDTO> resultado = controlador.consultarTodos();

        assertEquals(1, resultado.size());
        verify(consultarDireccionesManejador).ejecutar();
    }

    @Test
    void deberiaConsultarPorNombre() {
        String nombre = "Direccion Test";
        DireccionDTO dto = new DireccionDTO();
        when(consultarDireccionPorNombreDTOManejador.ejecutar(nombre)).thenReturn(dto);

        DireccionDTO resultado = controlador.consultarPorNombre(nombre);

        assertNotNull(resultado);
        verify(consultarDireccionPorNombreDTOManejador).ejecutar(nombre);
    }
}
