package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;

@Getter
public class ParticipanteExterno extends Participante {
    private ParticipanteExterno(UUID identificador, Miembro miembro) {
        super(identificador, miembro);
    }

    public static ParticipanteExterno construir(UUID identificador, Miembro miembro) {
        return new ParticipanteExterno(
                identificador,
                obtenerObjetoPorDefecto(miembro, Miembro.construir())
        );
    }

    public static ParticipanteExterno construir() {
        return new ParticipanteExterno(
                obtenerValorDefecto(),
                Miembro.construir()
        );
    }
}