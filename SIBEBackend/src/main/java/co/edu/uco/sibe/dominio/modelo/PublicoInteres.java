package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;

@Getter
public class PublicoInteres {
    private UUID identificador;
    private String nombre;

    public PublicoInteres(UUID identificador, String nombre) {
        this.identificador = identificador;
        this.nombre = nombre;
    }

    public static PublicoInteres construir(UUID identificador, String nombre) {
        return new PublicoInteres(
                identificador,
                obtenerTextoPorDefecto(nombre)
        );
    }

    public static PublicoInteres construir() {
        return new PublicoInteres(
                obtenerValorDefecto(),
                VACIO
        );
    }
}