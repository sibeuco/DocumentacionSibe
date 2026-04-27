package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.modelo.Indicador;
import co.edu.uco.sibe.dominio.dto.IndicadorDTO;
import co.edu.uco.sibe.dominio.testdatabuilder.IndicadorDTOTestDataBuilder;
import co.edu.uco.sibe.infraestructura.adaptador.dao.IndicadorDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.IndicadorEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.IndicadorMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndicadorRepositorioConsultaImplementacionTest {

    @Mock
    private IndicadorDAO indicadorDAO;

    @Mock
    private IndicadorMapeador indicadorMapeador;

    private IndicadorRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new IndicadorRepositorioConsultaImplementacion(indicadorDAO, indicadorMapeador);
    }

    @Test
    void deberiaExcluirIndicadoresGlobalesCuandoConsultaParaActividades() {
        List<IndicadorEntidad> entidades = List.of(new IndicadorEntidad());

        IndicadorDTO participacion = new IndicadorDTOTestDataBuilder().conNombre("Participación").construir();
        IndicadorDTO cobertura = new IndicadorDTOTestDataBuilder().conNombre("Cobertura").construir();
        IndicadorDTO satisfaccion = new IndicadorDTOTestDataBuilder().conNombre("Satisfacción grupos de Interés").construir();
        IndicadorDTO reduccion = new IndicadorDTOTestDataBuilder().conNombre("Reducción de quejas y reclamos").construir();

        when(indicadorDAO.findAll()).thenReturn(entidades);
        when(indicadorMapeador.construirDTOs(entidades)).thenReturn(List.of(participacion, cobertura, satisfaccion, reduccion));

        List<IndicadorDTO> resultado = repositorio.consultarDTOsParaActividades();

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().noneMatch(dto -> dto.getNombre().equals("Participación")));
        assertTrue(resultado.stream().noneMatch(dto -> dto.getNombre().equals("Cobertura")));
        assertTrue(resultado.stream().anyMatch(dto -> dto.getNombre().equals("Satisfacción grupos de Interés")));
        assertTrue(resultado.stream().anyMatch(dto -> dto.getNombre().equals("Reducción de quejas y reclamos")));
    }

    @Test
    void deberiaRetornarTodosLosIndicadoresCuandoNoHayGlobales() {
        List<IndicadorEntidad> entidades = List.of(new IndicadorEntidad());

        IndicadorDTO satisfaccion = new IndicadorDTOTestDataBuilder().conNombre("Satisfacción").construir();
        IndicadorDTO reduccion = new IndicadorDTOTestDataBuilder().conNombre("Reducción").construir();

        when(indicadorDAO.findAll()).thenReturn(entidades);
        when(indicadorMapeador.construirDTOs(entidades)).thenReturn(List.of(satisfaccion, reduccion));

        List<IndicadorDTO> resultado = repositorio.consultarDTOsParaActividades();

        assertEquals(2, resultado.size());
    }

    @Test
    void deberiaRetornarListaVaciaCuandoTodosSonGlobales() {
        List<IndicadorEntidad> entidades = List.of(new IndicadorEntidad());

        IndicadorDTO participacion = new IndicadorDTOTestDataBuilder().conNombre("Participación").construir();
        IndicadorDTO cobertura = new IndicadorDTOTestDataBuilder().conNombre("Cobertura").construir();

        when(indicadorDAO.findAll()).thenReturn(entidades);
        when(indicadorMapeador.construirDTOs(entidades)).thenReturn(List.of(participacion, cobertura));

        List<IndicadorDTO> resultado = repositorio.consultarDTOsParaActividades();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deberiaRetornarTodosLosIndicadoresCuandoConsultaDTOs() {
        List<IndicadorEntidad> entidades = List.of(new IndicadorEntidad());

        IndicadorDTO participacion = new IndicadorDTOTestDataBuilder().conNombre("Participación").construir();
        IndicadorDTO satisfaccion = new IndicadorDTOTestDataBuilder().conNombre("Satisfacción").construir();

        when(indicadorDAO.findAll()).thenReturn(entidades);
        when(indicadorMapeador.construirDTOs(entidades)).thenReturn(List.of(participacion, satisfaccion));

        List<IndicadorDTO> resultado = repositorio.consultarDTOs();

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().anyMatch(dto -> dto.getNombre().equals("Participación")));
    }

    @Test
    void deberiaRetornarPaginaDeIndicadoresCuandoConsultaDTOsPaginado() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "nombre"));
        IndicadorEntidad entidad = new IndicadorEntidad();
        Page<IndicadorEntidad> paginaEntidades = new PageImpl<>(List.of(entidad), pageRequest, 1);

        IndicadorDTO dto = new IndicadorDTOTestDataBuilder().conNombre("Participación").construir();
        Page<IndicadorDTO> paginaDTOs = new PageImpl<>(List.of(dto), pageRequest, 1);

        when(indicadorDAO.findAll(pageRequest)).thenReturn(paginaEntidades);
        when(indicadorMapeador.construirDTOsPaginado(paginaEntidades)).thenReturn(paginaDTOs);

        Page<IndicadorDTO> resultado = repositorio.consultarDTOsPaginado(0, 10);

        assertEquals(1, resultado.getContent().size());
        assertEquals(1, resultado.getTotalElements());
        assertEquals("Participación", resultado.getContent().get(0).getNombre());
        verify(indicadorDAO).findAll(pageRequest);
        verify(indicadorMapeador).construirDTOsPaginado(paginaEntidades);
    }

    @Test
    void deberiaRetornarIndicadorCuandoExisteIdentificador() {
        UUID id = UUID.randomUUID();
        IndicadorEntidad entidad = new IndicadorEntidad();
        Indicador modelo = mock(Indicador.class);

        when(indicadorDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(indicadorMapeador.construriModelo(entidad)).thenReturn(modelo);

        Indicador resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExisteIdentificador() {
        UUID id = UUID.randomUUID();

        when(indicadorDAO.findById(id)).thenReturn(Optional.empty());

        assertNull(repositorio.consultarPorIdentificador(id));
    }

    @Test
    void deberiaRetornarIndicadorCuandoExisteNombre() {
        String nombre = "Satisfacción";
        IndicadorEntidad entidad = new IndicadorEntidad();
        Indicador modelo = mock(Indicador.class);

        when(indicadorDAO.findByNombre(nombre)).thenReturn(entidad);
        when(indicadorMapeador.construriModelo(entidad)).thenReturn(modelo);

        Indicador resultado = repositorio.consultarPorNombre(nombre);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExisteNombre() {
        when(indicadorDAO.findByNombre("Inexistente")).thenReturn(null);

        assertNull(repositorio.consultarPorNombre("Inexistente"));
    }
}
