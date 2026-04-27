package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.ProyectoDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.ProyectoRepositorioConsulta;
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
class ConsultarProyectosManejadorTest {

    @Mock private ProyectoRepositorioConsulta proyectoRepositorioConsulta;

    private ConsultarProyectosManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarProyectosManejador(proyectoRepositorioConsulta);
    }

    @Test
    void deberiaConsultarProyectosPaginado() {
        Page<ProyectoDTO> esperado = new PageImpl<>(List.of(mock(ProyectoDTO.class)));
        when(proyectoRepositorioConsulta.consultarDTOsPaginado(0, 10)).thenReturn(esperado);

        Page<ProyectoDTO> resultado = manejador.ejecutar(0, 10);

        assertEquals(esperado, resultado);
        verify(proyectoRepositorioConsulta).consultarDTOsPaginado(0, 10);
    }

}
