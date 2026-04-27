package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;

@Getter
public class TipoIdentificacion {
    private UUID identificador;
    private String sigla;
    private String descripcion;

    private TipoIdentificacion(UUID identificador, String sigla, String descripcion) {
        this.identificador = identificador;
        this.sigla = sigla;
        this.descripcion = descripcion;
    }

    public static TipoIdentificacion construir(UUID identificador, String sigla, String descripcion) {
        return new TipoIdentificacion(
                identificador,
                obtenerTextoPorDefecto(sigla),
                obtenerTextoPorDefecto(descripcion)
        );
    }

    public static TipoIdentificacion construir() {
        return new TipoIdentificacion(
                obtenerValorDefecto(),
                VACIO,
                VACIO
        );
    }
}