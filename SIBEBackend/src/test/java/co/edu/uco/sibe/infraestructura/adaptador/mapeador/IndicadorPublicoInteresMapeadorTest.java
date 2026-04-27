package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.PublicoInteresDTO;
import co.edu.uco.sibe.dominio.modelo.PublicoInteres;
import co.edu.uco.sibe.infraestructura.adaptador.dao.IndicadorPublicoInteresDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.IndicadorPublicoInteresEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.PublicoInteresEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IndicadorPublicoInteresMapeadorTest {

    @Mock
    private PublicoInteresMapeador publicoInteresMapeador;

    @Mock
    private IndicadorPublicoInteresDAO indicadorPublicoInteresDAO;

    private IndicadorPublicoInteresMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new IndicadorPublicoInteresMapeador(publicoInteresMapeador, indicadorPublicoInteresDAO);
    }

    @Test
    void deberiaConstruirEntidadDesdePublicoInteres() {
        UUID id = UUID.randomUUID();
        PublicoInteres publicoInteres = PublicoInteres.construir(id, "Público Test");
        PublicoInteresEntidad piEntidad = new PublicoInteresEntidad(id, "Público Test");

        when(publicoInteresMapeador.construirEntidad(publicoInteres)).thenReturn(piEntidad);
        when(indicadorPublicoInteresDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        IndicadorPublicoInteresEntidad entidad = mapeador.construirEntidad(publicoInteres);

        assertNotNull(entidad);
        assertEquals(piEntidad, entidad.getPublicoInteres());
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        PublicoInteresEntidad piEntidad = new PublicoInteresEntidad(id, "Público Modelo");
        IndicadorPublicoInteresEntidad entidad = new IndicadorPublicoInteresEntidad(UUID.randomUUID(), piEntidad);
        PublicoInteres publicoInteres = PublicoInteres.construir(id, "Público Modelo");

        when(publicoInteresMapeador.construirModelo(piEntidad)).thenReturn(publicoInteres);

        PublicoInteres resultado = mapeador.construirModelo(entidad);

        assertEquals(id, resultado.getIdentificador());
    }

    @Test
    void deberiaActualizarEntidad() {
        UUID id = UUID.randomUUID();
        PublicoInteresEntidad piEntidad = new PublicoInteresEntidad(id, "Original");
        IndicadorPublicoInteresEntidad entidad = new IndicadorPublicoInteresEntidad(UUID.randomUUID(), piEntidad);
        PublicoInteres nuevoPI = PublicoInteres.construir(UUID.randomUUID(), "Nuevo");
        PublicoInteresEntidad nuevaEntidad = new PublicoInteresEntidad(UUID.randomUUID(), "Nuevo");

        when(publicoInteresMapeador.construirEntidad(nuevoPI)).thenReturn(nuevaEntidad);

        mapeador.actualizarEntidad(entidad, nuevoPI);

        assertEquals(nuevaEntidad, entidad.getPublicoInteres());
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID id = UUID.randomUUID();
        PublicoInteresEntidad piEntidad = new PublicoInteresEntidad(id, "Público DTO");
        IndicadorPublicoInteresEntidad entidad = new IndicadorPublicoInteresEntidad(UUID.randomUUID(), piEntidad);
        PublicoInteresDTO piDTO = new PublicoInteresDTO(id.toString(), "Público DTO");

        when(publicoInteresMapeador.construirDTO(piEntidad)).thenReturn(piDTO);

        PublicoInteresDTO resultado = mapeador.construirDTO(entidad);

        assertEquals(id.toString(), resultado.getIdentificador());
    }

    @Test
    void deberiaConstruirListaEntidades() {
        PublicoInteres pi1 = PublicoInteres.construir(UUID.randomUUID(), "PI1");
        PublicoInteresEntidad piEntidad = new PublicoInteresEntidad(UUID.randomUUID(), "PI1");

        when(publicoInteresMapeador.construirEntidad(any(PublicoInteres.class))).thenReturn(piEntidad);
        when(indicadorPublicoInteresDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        List<IndicadorPublicoInteresEntidad> entidades = mapeador.construirEntidades(List.of(pi1));

        assertEquals(1, entidades.size());
    }

    @Test
    void deberiaConstruirListaModelos() {
        UUID id = UUID.randomUUID();
        PublicoInteresEntidad piEntidad = new PublicoInteresEntidad(id, "PI1");
        IndicadorPublicoInteresEntidad entidad = new IndicadorPublicoInteresEntidad(UUID.randomUUID(), piEntidad);
        PublicoInteres pi = PublicoInteres.construir(id, "PI1");

        when(publicoInteresMapeador.construirModelo(piEntidad)).thenReturn(pi);

        List<PublicoInteres> modelos = mapeador.construirModelos(List.of(entidad));

        assertEquals(1, modelos.size());
    }

    @Test
    void deberiaConstruirListaDTOs() {
        UUID id = UUID.randomUUID();
        PublicoInteresEntidad piEntidad = new PublicoInteresEntidad(id, "PI1");
        IndicadorPublicoInteresEntidad entidad = new IndicadorPublicoInteresEntidad(UUID.randomUUID(), piEntidad);
        PublicoInteresDTO piDTO = new PublicoInteresDTO(id.toString(), "PI1");

        when(publicoInteresMapeador.construirDTO(piEntidad)).thenReturn(piDTO);

        List<PublicoInteresDTO> dtos = mapeador.construirDTOs(List.of(entidad));

        assertEquals(1, dtos.size());
    }

    @Test
    void deberiaActualizarEntidadesAgregandoNuevos() {
        UUID idExistente = UUID.randomUUID();
        UUID idNuevo = UUID.randomUUID();
        PublicoInteresEntidad piEntidadExistente = new PublicoInteresEntidad(idExistente, "Existente");
        IndicadorPublicoInteresEntidad entidadExistente = new IndicadorPublicoInteresEntidad(UUID.randomUUID(), piEntidadExistente);
        List<IndicadorPublicoInteresEntidad> entidades = new ArrayList<>(List.of(entidadExistente));

        PublicoInteres piExistente = PublicoInteres.construir(idExistente, "Existente");
        PublicoInteres piNuevo = PublicoInteres.construir(idNuevo, "Nuevo");
        PublicoInteresEntidad piEntidadNueva = new PublicoInteresEntidad(idNuevo, "Nuevo");

        when(publicoInteresMapeador.construirEntidad(piNuevo)).thenReturn(piEntidadNueva);
        when(indicadorPublicoInteresDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        mapeador.actualizarEntidades(entidades, List.of(piExistente, piNuevo));

        assertEquals(2, entidades.size());
    }
}
