package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.ParticipanteComando;
import co.edu.uco.sibe.dominio.modelo.*;
import co.edu.uco.sibe.dominio.puerto.consulta.MiembroRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.ParticipanteRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParticipanteFabricaTest {

    @Mock
    private MiembroRepositorioConsulta miembroRepositorioConsulta;
    @Mock
    private ParticipanteRepositorioConsulta participanteRepositorioConsulta;

    private ParticipanteFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new ParticipanteFabrica(miembroRepositorioConsulta, participanteRepositorioConsulta);
    }

    @Test
    void deberiaRetornarParticipanteExistenteSiYaParticipa() {
        UUID ejecucionId = UUID.randomUUID();
        Participante existente = Participante.construir(UUID.randomUUID(), Externo.construir());
        when(participanteRepositorioConsulta.consultarPorDocumentoYSemestre("123456", ejecucionId)).thenReturn(existente);

        ParticipanteComando comando = new ParticipanteComando(null, "Juan Perez", "123456");

        Participante resultado = fabrica.construir(comando, ejecucionId);

        assertEquals(existente, resultado);
    }

    @Test
    void deberiaConstruirParticipanteExternoCuandoIdentificadorEsNull() {
        UUID ejecucionId = UUID.randomUUID();
        when(participanteRepositorioConsulta.consultarPorDocumentoYSemestre("123456", ejecucionId)).thenReturn(null);
        when(participanteRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);
        when(miembroRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);

        ParticipanteComando comando = new ParticipanteComando(null, "Juan Perez", "123456");

        Participante resultado = fabrica.construir(comando, ejecucionId);

        assertNotNull(resultado);
        assertInstanceOf(ParticipanteExterno.class, resultado);
    }

    @Test
    void deberiaConstruirParticipanteEstudianteCuandoMiembroEsEstudiante() {
        UUID ejecucionId = UUID.randomUUID();
        Estudiante estudiante = Estudiante.construir(UUID.randomUUID(), "Maria Lopez", "654321",
                CiudadResidencia.construir(UUID.randomUUID(), "Medellin"), "CAR001", "F",
                java.time.LocalDate.of(2000, 1, 1), "Colombiana", "Soltera",
                "maria@personal.com", "maria@inst.com", "Ingenieria",
                "Ingenieria", 2020, "Sexto", 80, 4.2f, "Activo",
                "Presencial", 30, "Bus");
        when(participanteRepositorioConsulta.consultarPorDocumentoYSemestre("654321", ejecucionId)).thenReturn(null);
        when(participanteRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);
        when(miembroRepositorioConsulta.consultarPorIdentificacion("654321")).thenReturn(estudiante);

        ParticipanteComando comando = new ParticipanteComando(UUID.randomUUID().toString(), "Maria Lopez", "654321");

        Participante resultado = fabrica.construir(comando, ejecucionId);

        assertNotNull(resultado);
        assertInstanceOf(ParticipanteEstudiante.class, resultado);
    }

    @Test
    void deberiaConstruirParticipanteEmpleadoCuandoMiembroEsEmpleado() {
        UUID ejecucionId = UUID.randomUUID();
        Empleado empleado = Empleado.construir(UUID.randomUUID(), "Carlos Garcia", "789012",
                CiudadResidencia.construir(UUID.randomUUID(), "Bogota"), "CAR002", "M",
                RelacionLaboral.construir(UUID.randomUUID(), "PLT", "Planta"),
                CentroCostos.construir(UUID.randomUUID(), "CC01", "Centro"));
        when(participanteRepositorioConsulta.consultarPorDocumentoYSemestre("789012", ejecucionId)).thenReturn(null);
        when(participanteRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);
        when(miembroRepositorioConsulta.consultarPorIdentificacion("789012")).thenReturn(empleado);

        ParticipanteComando comando = new ParticipanteComando(UUID.randomUUID().toString(), "Carlos Garcia", "789012");

        Participante resultado = fabrica.construir(comando, ejecucionId);

        assertNotNull(resultado);
        assertInstanceOf(ParticipanteEmpleado.class, resultado);
    }

    @Test
    void deberiaLanzarExcepcionCuandoMiembroNoExiste() {
        UUID ejecucionId = UUID.randomUUID();
        when(participanteRepositorioConsulta.consultarPorDocumentoYSemestre("999999", ejecucionId)).thenReturn(null);
        when(participanteRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);
        when(miembroRepositorioConsulta.consultarPorIdentificacion("999999")).thenReturn(null);

        ParticipanteComando comando = new ParticipanteComando(UUID.randomUUID().toString(), "No Existe", "999999");

        assertThrows(ValorInvalidoExcepcion.class, () -> fabrica.construir(comando, ejecucionId));
    }

    @Test
    void deberiaConstruirListaDeParticipantes() {
        UUID ejecucionId = UUID.randomUUID();
        when(participanteRepositorioConsulta.consultarPorDocumentoYSemestre(any(), eq(ejecucionId))).thenReturn(null);
        when(participanteRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);
        when(miembroRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);

        ParticipanteComando cmd1 = new ParticipanteComando(null, "Externo Uno", "111111");
        ParticipanteComando cmd2 = new ParticipanteComando(null, "Externo Dos", "222222");

        List<Participante> resultado = fabrica.construirParticipantes(List.of(cmd1, cmd2), ejecucionId);

        assertEquals(2, resultado.size());
    }

    @Test
    void deberiaConstruirParticipanteGenericoCuandoMiembroEsInterno() {
        UUID ejecucionId = UUID.randomUUID();
        Interno interno = Interno.construir(UUID.randomUUID(), "Interno Gen", "555555",
                CiudadResidencia.construir(UUID.randomUUID(), "Cali"), "CAR003", "M");
        when(participanteRepositorioConsulta.consultarPorDocumentoYSemestre("555555", ejecucionId)).thenReturn(null);
        when(participanteRepositorioConsulta.consultarPorIdentificador(any())).thenReturn(null);
        when(miembroRepositorioConsulta.consultarPorIdentificacion("555555")).thenReturn(interno);

        ParticipanteComando comando = new ParticipanteComando(UUID.randomUUID().toString(), "Interno Gen", "555555");

        Participante resultado = fabrica.construir(comando, ejecucionId);

        assertNotNull(resultado);
    }
}
