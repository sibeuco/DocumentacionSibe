package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarMiembroPorIdCarnetManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarMiembroPorIdentificacionManejador;
import co.edu.uco.sibe.dominio.dto.MiembroDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MiembroConsultaControladorTest {

    @Mock private ConsultarMiembroPorIdentificacionManejador consultarMiembroPorIdentificacionManejador;
    @Mock private ConsultarMiembroPorIdCarnetManejador consultarMiembroPorIdCarnetManejador;

    private MiembroConsultaControlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new MiembroConsultaControlador(consultarMiembroPorIdentificacionManejador, consultarMiembroPorIdCarnetManejador);
    }

    @Test
    void deberiaConsultarPorIdentificacion() {
        String identificacion = "123456789";
        MiembroDTO dto = new MiembroDTO();
        when(consultarMiembroPorIdentificacionManejador.ejecutar(identificacion)).thenReturn(dto);

        MiembroDTO resultado = controlador.consultarPorIdentificacion(identificacion);

        assertNotNull(resultado);
        verify(consultarMiembroPorIdentificacionManejador).ejecutar(identificacion);
    }

    @Test
    void deberiaConsultarPorIdCarnet() {
        String carnet = "CARNET-001";
        MiembroDTO dto = new MiembroDTO();
        when(consultarMiembroPorIdCarnetManejador.ejecutar(carnet)).thenReturn(dto);

        MiembroDTO resultado = controlador.consultarPorIdCarnet(carnet);

        assertNotNull(resultado);
        verify(consultarMiembroPorIdCarnetManejador).ejecutar(carnet);
    }
}
