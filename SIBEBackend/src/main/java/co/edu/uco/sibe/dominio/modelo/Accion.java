package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;

@Getter
public class Accion {
    private UUID identificador;
    private String detalle;
    private String objetivo;

    private Accion(UUID identificador, String detalle, String objetivo) {
        this.identificador = identificador;
        this.detalle = detalle;
        this.objetivo = objetivo;
    }

    public static Accion construir(UUID identificador, String detalle, String objetivo) {
        return new Accion(
                identificador,
                obtenerTextoPorDefecto(detalle),
                obtenerTextoPorDefecto(objetivo)
        );
    }

    public static Accion construir() {
        return new Accion(
                obtenerValorDefecto(),
                VACIO,
                VACIO
        );
    }
}