package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;

@Getter
public class Miembro {
    private UUID identificador;
    private String nombreCompleto;
    private String numeroIdentificacion;

    protected Miembro(UUID identificador, String nombreCompleto, String numeroIdentificacion) {
        this.identificador = identificador;
        this.nombreCompleto = nombreCompleto;
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public static Miembro construir(UUID identificador, String nombreCompleto, String numeroIdentificacion) {
        return new Miembro(
                identificador,
                obtenerTextoPorDefecto(nombreCompleto),
                obtenerTextoPorDefecto(numeroIdentificacion)
        );
    }

    public static Miembro construir() {
        return new Miembro(
                obtenerValorDefecto(),
                VACIO,
                VACIO
        );
    }
}