package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;

@Getter
public class RegistroAsistencia {
    private UUID identificador;
    private EjecucionActividad ejecucionActividad;
    private Participante participante;

    private RegistroAsistencia(UUID identificador, EjecucionActividad ejecucionActividad, Participante participante) {
        this.identificador = identificador;
        this.ejecucionActividad = ejecucionActividad;
        this.participante = participante;
    }

    public static RegistroAsistencia construir(UUID identificador, EjecucionActividad ejecucionActividad, Participante participante) {
        return new RegistroAsistencia(
                identificador,
                obtenerObjetoPorDefecto(ejecucionActividad, EjecucionActividad.construir()),
                obtenerObjetoPorDefecto(participante, Participante.construir())
        );
    }

    public static RegistroAsistencia construir() {
        return new RegistroAsistencia(
                obtenerValorDefecto(),
                EjecucionActividad.construir(),
                Participante.construir()
        );
    }
}