package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;

@Getter
public class Externo extends Miembro {
    private Externo(UUID identificador, String nombreCompleto, String numeroIdentificacion) {
        super(identificador, nombreCompleto, numeroIdentificacion);
    }

    public static Externo construir(UUID identificador, String nombreCompleto, String numeroIdentificacion) {
        return new Externo(
                identificador,
                obtenerTextoPorDefecto(nombreCompleto),
                obtenerTextoPorDefecto(numeroIdentificacion)
        );
    }

    public static Externo construir() {
        return new Externo(
                obtenerValorDefecto(),
                VACIO,
                VACIO
        );
    }
}