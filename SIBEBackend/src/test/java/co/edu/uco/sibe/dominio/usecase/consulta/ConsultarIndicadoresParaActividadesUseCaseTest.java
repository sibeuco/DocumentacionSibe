package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.IndicadorDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.IndicadorRepositorioConsulta;
import co.edu.uco.sibe.dominio.testdatabuilder.IndicadorDTOTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarIndicadoresParaActividadesUseCaseTest {

    @Mock
    private IndicadorRepositorioConsulta indicadorRepositorioConsulta;

    private ConsultarIndicadoresParaActividadesUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarIndicadoresParaActividadesUseCase(indicadorRepositorioConsulta);
    }

    @Test
    void deberiaRetornarIndicadoresParaActividadesCuandoExisten() {
        List<IndicadorDTO> indicadoresEsperados = List.of(
                new IndicadorDTOTestDataBuilder().conNombre("Satisfacción").construir(),
                new IndicadorDTOTestDataBuilder().conNombre("Reducción de quejas").construir()
        );
        when(indicadorRepositorioConsulta.consultarDTOsParaActividades()).thenReturn(indicadoresEsperados);

        List<IndicadorDTO> resultado = useCase.ejecutar();

        assertEquals(2, resultado.size());
        verify(indicadorRepositorioConsulta).consultarDTOsParaActividades();
    }

    @Test
    void deberiaRetornarListaVaciaCuandoNoExistenIndicadores() {
        when(indicadorRepositorioConsulta.consultarDTOsParaActividades()).thenReturn(List.of());

        List<IndicadorDTO> resultado = useCase.ejecutar();

        assertTrue(resultado.isEmpty());
        verify(indicadorRepositorioConsulta).consultarDTOsParaActividades();
    }
}
