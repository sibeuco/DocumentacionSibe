package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.modelo.RelacionLaboral;
import co.edu.uco.sibe.infraestructura.adaptador.dao.RelacionLaboralDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.RelacionLaboralEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.RelacionLaboralMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RelacionLaboralRepositorioConsultaImplementacionTest {

    @Mock private RelacionLaboralDAO relacionLaboralDAO;
    @Mock private RelacionLaboralMapeador relacionLaboralMapeador;

    private RelacionLaboralRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new RelacionLaboralRepositorioConsultaImplementacion(relacionLaboralDAO, relacionLaboralMapeador);
    }

    @Test
    void deberiaRetornarRelacionLaboralCuandoExisteIdentificador() {
        UUID id = UUID.randomUUID();
        RelacionLaboralEntidad entidad = new RelacionLaboralEntidad();
        RelacionLaboral modelo = mock(RelacionLaboral.class);

        when(relacionLaboralDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(relacionLaboralMapeador.construirModelo(entidad)).thenReturn(modelo);

        RelacionLaboral resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExisteIdentificador() {
        UUID id = UUID.randomUUID();

        when(relacionLaboralDAO.findById(id)).thenReturn(Optional.empty());

        assertNull(repositorio.consultarPorIdentificador(id));
    }

    @Test
    void deberiaRetornarRelacionLaboralCuandoExisteCodigo() {
        String codigo = "RL001";
        RelacionLaboralEntidad entidad = new RelacionLaboralEntidad();
        RelacionLaboral modelo = mock(RelacionLaboral.class);

        when(relacionLaboralDAO.findFirstByCodigo(codigo)).thenReturn(entidad);
        when(relacionLaboralMapeador.construirModelo(entidad)).thenReturn(modelo);

        RelacionLaboral resultado = repositorio.consultarPorCodigo(codigo);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExisteCodigo() {
        when(relacionLaboralDAO.findFirstByCodigo("INVALIDO")).thenReturn(null);

        assertNull(repositorio.consultarPorCodigo("INVALIDO"));
    }
}
