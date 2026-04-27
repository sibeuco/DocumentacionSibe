package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;

@Getter
public class Participante {
    private UUID identificador;
    private Miembro miembro;

    protected Participante(UUID identificador, Miembro miembro) {
        this.identificador = identificador;
        this.miembro = miembro;
    }

    public static Participante construir(UUID identificador, Miembro miembro) {
        return new Participante(
                identificador,
                obtenerObjetoPorDefecto(miembro, Miembro.construir())
        );
    }

    public static Participante construir() {
        return new Participante(
                obtenerValorDefecto(),
                Miembro.construir()
        );
    }
}