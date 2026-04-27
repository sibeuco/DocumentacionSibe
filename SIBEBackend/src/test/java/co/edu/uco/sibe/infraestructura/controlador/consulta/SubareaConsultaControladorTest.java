package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarSubareaDetalladaManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarSubareaPorNombreDTOManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarSubareasDTOManejador;
import co.edu.uco.sibe.dominio.dto.SubareaDTO;
import co.edu.uco.sibe.dominio.dto.SubareaDetalladaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubareaConsultaControladorTest {

    @Mock private ConsultarSubareasDTOManejador consultarSubareasDTOManejador;
    @Mock private ConsultarSubareaDetalladaManejador consultarSubareaDetalladaManejador;
    @Mock private ConsultarSubareaPorNombreDTOManejador consultarSubareaPorNombreDTOManejador;

    private SubareaConsultaControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new SubareaConsultaControlador(consultarSubareasDTOManejador, consultarSubareaDetalladaManejador, consultarSubareaPorNombreDTOManejador);
    }

    @Test
    void deberiaConsultarDetalle() {
        String identificador = "sub-1";
        SubareaDetalladaDTO dto = new SubareaDetalladaDTO();
        when(consultarSubareaDetalladaManejador.ejecutar(identificador)).thenReturn(dto);

        SubareaDetalladaDTO resultado = controlador.consultarDetalle(identificador);

        assertNotNull(resultado);
        verify(consultarSubareaDetalladaManejador).ejecutar(identificador);
    }

    @Test
    void deberiaConsultarTodos() {
        List<SubareaDTO> subareas = List.of(new SubareaDTO());
        when(consultarSubareasDTOManejador.ejecutar()).thenReturn(subareas);

        List<SubareaDTO> resultado = controlador.consultarTodos();

        assertEquals(1, resultado.size());
        verify(consultarSubareasDTOManejador).ejecutar();
    }

    @Test
    void deberiaConsultarPorNombre() {
        String nombre = "Subarea Test";
        SubareaDTO dto = new SubareaDTO();
        when(consultarSubareaPorNombreDTOManejador.ejecutar(nombre)).thenReturn(dto);

        SubareaDTO resultado = controlador.consultarPorNombre(nombre);

        assertNotNull(resultado);
        verify(consultarSubareaPorNombreDTOManejador).ejecutar(nombre);
    }
}
