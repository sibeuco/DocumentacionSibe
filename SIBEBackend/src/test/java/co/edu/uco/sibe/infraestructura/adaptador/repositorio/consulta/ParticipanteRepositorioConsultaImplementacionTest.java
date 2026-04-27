package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.modelo.Participante;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EjecucionActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.ParticipanteDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ActividadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EjecucionActividadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ParticipanteEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.ParticipanteMapeador;
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
class ParticipanteRepositorioConsultaImplementacionTest {

    @Mock private ParticipanteDAO participanteDAO;
    @Mock private ParticipanteMapeador participanteMapeador;
    @Mock private EjecucionActividadDAO ejecucionActividadDAO;

    private ParticipanteRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new ParticipanteRepositorioConsultaImplementacion(participanteDAO, participanteMapeador, ejecucionActividadDAO);
    }

    @Test
    void deberiaConsultarPorIdentificadorExistente() {
        UUID id = UUID.randomUUID();
        ParticipanteEntidad entidad = mock(ParticipanteEntidad.class);
        Participante modelo = mock(Participante.class);
        when(participanteDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(participanteMapeador.construirModelo(entidad)).thenReturn(modelo);

        Participante resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoIdentificadorNoExiste() {
        UUID id = UUID.randomUUID();
        when(participanteDAO.findById(id)).thenReturn(Optional.empty());

        Participante resultado = repositorio.consultarPorIdentificador(id);

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarPorDocumentoMiembroExistente() {
        ParticipanteEntidad entidad = mock(ParticipanteEntidad.class);
        Participante modelo = mock(Participante.class);
        when(participanteDAO.findByMiembroNumeroIdentificacion("123")).thenReturn(entidad);
        when(participanteMapeador.construirModelo(entidad)).thenReturn(modelo);

        Participante resultado = repositorio.consultarPorDocumentoMiembro("123");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoDocumentoMiembroNoExiste() {
        when(participanteDAO.findByMiembroNumeroIdentificacion("999")).thenReturn(null);

        Participante resultado = repositorio.consultarPorDocumentoMiembro("999");

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarPorDocumentoYSemestreExistente() {
        UUID ejecucionId = UUID.randomUUID();
        EjecucionActividadEntidad ejecucion = mock(EjecucionActividadEntidad.class);
        ActividadEntidad actividad = mock(ActividadEntidad.class);
        ParticipanteEntidad entidad = mock(ParticipanteEntidad.class);
        Participante modelo = mock(Participante.class);

        when(ejecucionActividadDAO.findById(ejecucionId)).thenReturn(Optional.of(ejecucion));
        when(ejecucion.getActividad()).thenReturn(actividad);
        when(actividad.getSemestre()).thenReturn("2026-1");
        when(participanteDAO.findByDocumentoAndSemestre("123", "2026-1")).thenReturn(Optional.of(entidad));
        when(participanteMapeador.construirModelo(entidad)).thenReturn(modelo);

        Participante resultado = repositorio.consultarPorDocumentoYSemestre("123", ejecucionId);

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoEjecucionNoExiste() {
        UUID ejecucionId = UUID.randomUUID();
        when(ejecucionActividadDAO.findById(ejecucionId)).thenReturn(Optional.empty());

        Participante resultado = repositorio.consultarPorDocumentoYSemestre("123", ejecucionId);

        assertNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoParticipanteNoExisteEnSemestre() {
        UUID ejecucionId = UUID.randomUUID();
        EjecucionActividadEntidad ejecucion = mock(EjecucionActividadEntidad.class);
        ActividadEntidad actividad = mock(ActividadEntidad.class);

        when(ejecucionActividadDAO.findById(ejecucionId)).thenReturn(Optional.of(ejecucion));
        when(ejecucion.getActividad()).thenReturn(actividad);
        when(actividad.getSemestre()).thenReturn("2026-1");
        when(participanteDAO.findByDocumentoAndSemestre("123", "2026-1")).thenReturn(Optional.empty());

        Participante resultado = repositorio.consultarPorDocumentoYSemestre("123", ejecucionId);

        assertNull(resultado);
    }
}
