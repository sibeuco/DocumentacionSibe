package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.AccionDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.AccionRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarAccionesManejadorTest {

    @Mock private AccionRepositorioConsulta accionRepositorioConsulta;

    private ConsultarAccionesManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarAccionesManejador(accionRepositorioConsulta);
    }

    @Test
    void deberiaConsultarAccionesPaginado() {
        Page<AccionDTO> esperado = new PageImpl<>(List.of(mock(AccionDTO.class)));
        when(accionRepositorioConsulta.consultarDTOsPaginado(0, 10)).thenReturn(esperado);

        Page<AccionDTO> resultado = manejador.ejecutar(0, 10);

        assertEquals(esperado, resultado);
        verify(accionRepositorioConsulta).consultarDTOsPaginado(0, 10);
    }

}
