package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.TipoIndicadorComando;
import co.edu.uco.sibe.dominio.modelo.TipoIndicador;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIndicadorRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoIndicadorFabricaTest {

    @Mock
    private TipoIndicadorRepositorioConsulta tipoIndicadorRepositorioConsulta;

    private TipoIndicadorFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new TipoIndicadorFabrica(tipoIndicadorRepositorioConsulta);
    }

    @Test
    void deberiaConstruirTipoIndicadorDesdeComando() {
        when(tipoIndicadorRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);
        TipoIndicadorComando comando = new TipoIndicadorComando("Eficacia", "Resultado");

        TipoIndicador resultado = fabrica.construir(comando);

        assertNotNull(resultado.getIdentificador());
        assertEquals("Eficacia", resultado.getNaturaleza());
        assertEquals("Resultado", resultado.getTipologia());
    }
}
