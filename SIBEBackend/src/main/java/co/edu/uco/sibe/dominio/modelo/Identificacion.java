package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;

@Getter
public class Identificacion {
    private UUID identificador;
    private String numeroIdentificacion;
    private TipoIdentificacion tipoIdentificacion;

    private Identificacion(UUID identificador, String numeroIdentificacion, TipoIdentificacion tipoIdentificacion) {
        this.identificador = identificador;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public static Identificacion construir(UUID identificador, String numeroIdentificacion, TipoIdentificacion tipoIdentificacion) {
        return new Identificacion(
                identificador,
                obtenerTextoPorDefecto(numeroIdentificacion),
                obtenerObjetoPorDefecto(tipoIdentificacion, TipoIdentificacion.construir())
        );
    }

    public static Identificacion construir() {
        return new Identificacion(
                obtenerValorDefecto(),
                VACIO,
                TipoIdentificacion.construir()
        );
    }
}