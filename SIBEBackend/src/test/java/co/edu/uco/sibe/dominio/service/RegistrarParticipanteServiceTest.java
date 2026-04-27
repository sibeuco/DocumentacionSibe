package co.edu.uco.sibe.dominio.service;

import co.edu.uco.sibe.dominio.modelo.Miembro;
import co.edu.uco.sibe.dominio.modelo.Participante;
import co.edu.uco.sibe.dominio.puerto.comando.ParticipanteRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.ParticipanteRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrarParticipanteServiceTest {

    @Mock
    private ParticipanteRepositorioConsulta participanteRepositorioConsulta;
    @Mock
    private ParticipanteRepositorioComando participanteRepositorioComando;

    private RegistrarParticipanteService service;

    @BeforeEach
    void setUp() {
        service = new RegistrarParticipanteService(participanteRepositorioConsulta, participanteRepositorioComando);
    }

    @Test
    void deberiaGuardarNuevoParticipanteCuandoNoExiste() {
        Participante participante = mock(Participante.class);
        Miembro miembro = mock(Miembro.class);
        when(participante.getMiembro()).thenReturn(miembro);
        when(miembro.getNumeroIdentificacion()).thenReturn("123456");

        when(participanteRepositorioConsulta.consultarPorDocumentoMiembro("123456")).thenReturn(null);

        Participante resultado = service.ejecutar(participante);

        assertEquals(participante, resultado);
        verify(participanteRepositorioComando).guardar(participante);
    }

    @Test
    void deberiaRetornarParticipanteExistenteCuandoYaExiste() {
        Participante participante = mock(Participante.class);
        Participante participanteExistente = mock(Participante.class);
        Miembro miembro = mock(Miembro.class);
        when(participante.getMiembro()).thenReturn(miembro);
        when(miembro.getNumeroIdentificacion()).thenReturn("123456");

        when(participanteRepositorioConsulta.consultarPorDocumentoMiembro("123456"))
                .thenReturn(participanteExistente);

        Participante resultado = service.ejecutar(participante);

        assertEquals(participanteExistente, resultado);
        verify(participanteRepositorioComando, never()).guardar(any());
    }
}
